package org.apollo.template.Controller.Booking;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Booking;
import org.apollo.template.Service.Utility.TimeUtils;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class BookingCompleteController implements Subscriber, Initializable {

    @FXML
    private Label label_status, label_statusMessage, label_meetingType, label_meetingTime, label_meetingDate, label_bookerName;

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

            label_meetingTime.setText(TimeUtils.getStringTimeFormatted(booking));

            label_status.setText("SUCCES!");

            label_bookerName.setText(booking.getUsername());

            label_statusMessage.setText("Lokal er nu booket for dit møde:");

            label_meetingType.setText(booking.getMeetingType().getMeetingTypeName());
            label_meetingDate.setText(booking.getDate().toString());

            // unsubscribing to messages broker
            Platform.runLater(this::unsub);

        }
    }

    private void unsub(){
        // unsubscribing to messages broker
        MessagesBroker.getInstance().unSubscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);
    }

    /**
     * Method for sending the user back to info screen view.
     */
    @FXML
    protected void onButton_Back(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
    }

}
