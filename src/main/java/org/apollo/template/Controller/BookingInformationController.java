package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.TextFieldInputValidation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingInformationDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.MeetingTypeDBDAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.AddEmailIfNotExists;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetEmailIDByEmailAdress;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetMeetingTypeIDByMeetingType;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingInformationController implements Initializable, Subscriber {
    
    @FXML
    private TextField textField_name, textField_email, textfield_numberOfParticipants;

    @FXML
    private CheckBox checkBoxTOS;

    @FXML
    private ChoiceBox<MeetingType> choiceBox_meetingType;
    private BookingInformation bookingInformation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribing to messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        // atatching input validation to textfield
        TextFieldInputValidation.attatchIntegerValidation(textfield_numberOfParticipants);

        // loading choisebox.
        loadMeetingTypeCB();

        // setting up broker
    }

    /**
     * Method for loading meeting types into choiceBox_meetingType.
     */
    private void loadMeetingTypeCB(){

        // creating instance of dao.
        DAO<MeetingType> dao = new MeetingTypeDBDAO();

        // clearing the content of choiceBox to avoid replica data.
        choiceBox_meetingType.getItems().clear();

        // loading meeting types from dao into choiceBox.
        choiceBox_meetingType.getItems().addAll(dao.readAll());
    }


    @Override
    public void update(BookingInformation bookingInformation) {

        this.bookingInformation = bookingInformation;


    }


    // region buttons

    @FXML
    protected void onButton_book(){

        // checks if a meeting type has been selected.
        if (choiceBox_meetingType.getSelectionModel().getSelectedItem() == null) {
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en møde type!")
                    .start();
            return;
        }

        // checking if a valid email has been entered.
        if (!EmailValidator.validateEmail(textField_email.getText())){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Den indtastet email opfylder " +
                    "ikke kravne. Mailen skal være tilhørende EASV").start();
            return;
        }

        // checking if number of participants has been selected.
        if (textfield_numberOfParticipants.getLength() <= 0) {
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Der er ikke valgt antal deltager!")
                    .start();

            return;
        }

        // checks if TOS has been accepted
        if (!checkBoxTOS.isSelected()){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du kan ikke booke et lokale hvis du ikke acceptere TOS.")
                    .start();
            return;
        }

        // creating email
        Email email = new Email(textField_email.getText());

        // check if email exists in the database and creates it if not through SP.
        AddEmailIfNotExists.addEmailIfNotExists(email);

        // converting textfield_numberOfParticipants to int.
        int numberOfParticipants = Integer.valueOf(textfield_numberOfParticipants.getText());

        // create bookingInformation obj.
        bookingInformation.setUserName(textField_name.getText());
        bookingInformation.setEmail(email);
        bookingInformation.setNumberOfParticipants(numberOfParticipants);
        bookingInformation.setMeetingType(choiceBox_meetingType.getSelectionModel().getSelectedItem());

        System.out.println(bookingInformation.getStartTime());
        System.out.println(bookingInformation.getEndTime());
        System.out.println("UPDATING");

        // Check if adhoc
        if (bookingInformation.getAdhocBool()) {
            // Sets the department to adhoc
            bookingInformation.setDepartmentID(2);
            // Sets the team to anonymous
            bookingInformation.setTeamId(2);
        }

        // Inserts the meetingTypeID
        bookingInformation.setMeetingTypeID(GetMeetingTypeIDByMeetingType.
                getMeetingTypeIDByMeetingType(bookingInformation.getMeetingType()));

        // Inserts the userID
        bookingInformation.setUserID(GetEmailIDByEmailAdress.getEmailIDByEmailName(email));

        DAO<BookingInformation> bookingInformationDAO = new BookingInformationDAO();
        bookingInformationDAO.add(bookingInformation);
        System.out.println("Booking inserted");

        // sending the user to booking complite view.
        MainController.getInstance().setView(ViewList.BOOKINGCOMPLITE, BorderPaneRegion.CENTER);

        // publish bookingInformation obj.
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, bookingInformation);


    }

    // endregion

}
