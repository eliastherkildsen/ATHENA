package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCompliteController implements Subscriber, Initializable {

    @FXML
    private Label lable_status, lable_statuseMessage, lable_meetingType, lable_meetingTime, lable_meetingDate;

    @FXML
    private Button button_back;

    private Booking booking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribing to messages broker for topic BOOKING_INFORMATION
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

    }

    @Override
    public void update(Object o) {
        // validating that the object o is instanceof Bookinginformation
        if (o instanceof Booking) {
            this.booking = (Booking) o;

            // setting labels.
            //lable_meetingDate.setText(bookingInformation.getDate().toString());

            lable_meetingTime.setText(booking.getStartTime() + " - " + booking.getEndTime());

            lable_status.setText("SUCESS");

            lable_statuseMessage.setText("Du har nu booket et lokale");

            lable_meetingType.setText(booking.getMeetingType().getMeetingTypeName());

            lable_meetingDate.setText(booking.getDate().toString());

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
