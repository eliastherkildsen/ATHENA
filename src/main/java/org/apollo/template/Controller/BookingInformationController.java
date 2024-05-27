package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.persistence.DAO;
import org.apollo.template.persistence.MeetingTypeDAO.MeetingTypeDBDAO;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingInformationController implements Initializable {

    @FXML
    private TextField textField_name, textField_email, textfield_numberOfParticipants;

    @FXML
    private ChoiceBox<MeetingType> choiceBox_meetingType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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


}
