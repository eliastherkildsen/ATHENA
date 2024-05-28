package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

public class BookingCompliteController {

    @FXML
    private Label lable_status, lable_statuseMessage, lable_meetingType, lable_meetingTime, lable_meetingDate;

    @FXML
    private Button button_back;


    /**
     * Method for sending the user back to info screen view.
     */
    @FXML
    protected void onButton_Back(){
        //TODO: make this return the user to booked roomes view.
        MainController.getInstance().setView(ViewList.SYSTEMCHOSE, BorderPaneRegion.CENTER);
    }

}
