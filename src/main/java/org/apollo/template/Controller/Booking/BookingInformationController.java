package org.apollo.template.Controller.Booking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.TextFieldInputValidation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.MeetingTypeDAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.AddEmailIfNotExists;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetEmailIDByEmailAdress;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BookingInformationController implements Initializable, Subscriber {
    
    @FXML
    private TextField textField_name, textField_email, textfield_numberOfParticipants;

    @FXML
    private CheckBox checkBoxTOS;

    @FXML
    private ChoiceBox<MeetingType> choiceBox_meetingType;
    private Booking booking;
    private List<Booking> bookingList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribing to messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION_BATCH);

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
        DAO<MeetingType> dao = new MeetingTypeDAO();

        // clearing the content of choiceBox to avoid replica data.
        choiceBox_meetingType.getItems().clear();

        // loading meeting types from dao into choiceBox.
        choiceBox_meetingType.getItems().addAll(dao.readAll());
    }


    @Override
    public void update(Object o) {
        // validates that o is an instance of booking
        if (o instanceof Booking) {
            this.booking = (Booking) o;
        }

        // validates that o is an instance of a list then that it's a list with booking objects.
        if (o instanceof List<?>) {
            List<?> tempList = (List<?>) o;
            if (!tempList.isEmpty() && tempList.get(0) instanceof Booking) {
                this.bookingList = (List<Booking>) o;
                this.booking = (Booking) tempList.get(0);
            }
        }

        if (booking.getNumberOfParticipants() > 0);{
            textfield_numberOfParticipants.setText(String.valueOf(booking.getNumberOfParticipants()));
        }

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

        if (!booking.isAdHoc()){
            for (Booking bookingListObject : bookingList) {
                bookingListObject = generateBooking(bookingListObject, email, numberOfParticipants);
                DAO<Booking> bookingDAO = new BookingDAO();
                bookingDAO.add(bookingListObject);
            }
            MainController.getInstance().setView(ViewList.ADMINCREATEBOOKING, BorderPaneRegion.CENTER);
            new Alert(MainController.getInstance(), 8, AlertType.SUCCESS, "SUCCES! \nDin Booking er nu registeret!").start();
            LoggerMessage.trace(this, "Example of Booking \n" + booking);
            MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION_BATCH, booking);

        } else {
            // create bookingInformation obj.
            booking = generateBooking(booking, email, numberOfParticipants);
            DAO<Booking> bookingDAO = new BookingDAO();
            bookingDAO.add(booking);

            LoggerMessage.debug(this, "Insertet new booking: " + booking.toString());

            // sending the user to booking complite view.
            MainController.getInstance().setView(ViewList.BOOKINGCOMPLITE, BorderPaneRegion.CENTER);
            // publish bookingInformation obj.
            MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, booking);
        }

    }

    private Booking generateBooking (Booking booking, Email email, int numberOfParticipants){
        booking.setUsername(textField_name.getText());
        booking.setEmail(email);
        booking.setNumberOfParticipants(numberOfParticipants);
        booking.setMeetingType(choiceBox_meetingType.getSelectionModel().getSelectedItem());

        // Inserts the meetingTypeID
        MeetingType meetingType = choiceBox_meetingType.getSelectionModel().getSelectedItem();

        booking.setMeetingType(meetingType);

        // Inserts the userID
        booking.getEmail().setEmailID(GetEmailIDByEmailAdress.getEmailIDByEmailName(email));
        return booking;
    }
    // endregion
}
