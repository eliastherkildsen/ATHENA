package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.model.MeetingType;

public class BookingInformationController {

    @FXML
    private TextField textField_name, textField_email, textfield_numberOfParticipants;

    @FXML
    private ChoiceBox<MeetingType> choiceBox_meetingType;

}
