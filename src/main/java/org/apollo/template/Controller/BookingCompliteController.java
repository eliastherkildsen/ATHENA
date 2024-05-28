package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.MessagesBroker;
import org.apollo.template.persistence.MessagesBrokerTopic;
import org.apollo.template.persistence.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCompliteController implements Subscriber, Initializable {

    @FXML
    private Label lable_status, lable_statuseMessage, lable_meetingType, lable_meetingTime, lable_meetingDate;

    @FXML
    private Button button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribing to messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

    }

    @Override
    public void update(Object o) {

        // check if the obj is of type bookingInformation
        if (o instanceof BookingInformation){

            //fetching data from BookingInformation(o)

            String meetingDate = String.valueOf(((BookingInformation) o).getDate());
            String meetingTime = String.valueOf(((BookingInformation) o).getStartTime() + " - "
                    + ((BookingInformation) o).getEndTime());

            String meetingType = String.valueOf(((BookingInformation) o).getMeetingType());


            // setting labels.
            lable_meetingDate.setText(meetingDate);

            lable_meetingTime.setText(meetingTime);

            lable_status.setText("SUCESS");

            lable_statuseMessage.setText("Du har nu booket et lokale lol!");

            lable_meetingType.setText(meetingType);

        }

    }

    /**
     * Method for sending the user back to info screen view.
     */
    @FXML
    protected void onButton_Back(){
        //TODO: make this return the user to booked roomes view.
        MainController.getInstance().setView(ViewList.SYSTEMCHOSE, BorderPaneRegion.CENTER);
    }

}
