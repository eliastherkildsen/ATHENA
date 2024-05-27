package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.TextFieldInputValidation;
import org.apollo.template.persistence.DAO;
import org.apollo.template.persistence.MeetingTypeDBDAO;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookingInformationController implements Initializable {

    @FXML
    private TextField textField_name, textField_email, textfield_numberOfParticipants;

    @FXML
    private CheckBox checkBoxTOS;

    @FXML
    private ChoiceBox<MeetingType> choiceBox_meetingType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // atatching input validation to textfield
        TextFieldInputValidation.attatchIntegerValidation(textfield_numberOfParticipants);
        // loading choisebox.
        loadMeetingTypeCB();
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

        // check if email exists in the database and creates it if not through SP.
        // TODO: placing SQL in the controller? needs second opinion.
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC AddEmailIfNotExists @EmailAddress = ?");
            ps.setString(1, textField_email.getText());
            ResultSet rs = ps.executeQuery();
            LoggerMessage.debug(this, "JDBC: " + rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    // endregion

}
