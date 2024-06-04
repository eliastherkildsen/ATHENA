package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Booking;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ReservedRoomsVBox extends VBox {

    List<BookingComp> bookingComps = new ArrayList<>();
    /**
     * Constructor to create a VBox displaying reservation information.
     * @param bookingList List of Booking objects containing booking information.
     */
    public ReservedRoomsVBox(List<Booking> bookingList) {
        // Set the background color of the VBox to transparent blue
        this.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        if (bookingList == null){

            LoggerMessage.warning(this,"Booking Array Cannot be NULL");

        } else {

            // Set alignment and padding for the VBox
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(10));

            LoggerMessage.info(this, "Attempting to generate VBox with Bookings.");

            for (Booking i : bookingList) {
                //Getting our Variables sorted.
                String time = TimeUtils.getStringTimeFormatted(i);
                String roomName = i.getRoom().getRoomName();
                String userName = i.getUsername();
                String meetingType = i.getMeetingType().getMeetingTypeName();
                int bookingId = i.getBookingID();

                BookingComp bookingComponent;
                LoggerMessage.trace(this, roomName + " | " + userName + " | Adding to vbox");


                if (bookingId == -1){
                    LoggerMessage.debug(this,"Making BookingComp without ID");
                    bookingComponent = new BookingComp(roomName,userName,meetingType,time);
                } else {
                    LoggerMessage.debug(this,"Making BookingComp with ID: " + bookingId);
                    bookingComponent = new BookingComp(roomName,userName,meetingType,time,bookingId);
                }

                // Set margin for each booking component
                LoggerMessage.debug(this,"Trying to set Margin for Component : " + bookingComponent);
                this.setMargin(bookingComponent, new Insets(10));

                LoggerMessage.debug(this,"Trying to add Component : " + bookingComponent + " To list " + bookingComps);
                bookingComps.add(bookingComponent);

                LoggerMessage.debug(this,"Attempting to add Component to myself: " + bookingComponent);
                this.getChildren().add(bookingComponent);

                LoggerMessage.debug(this,"Component added successfully");
            }
        }
    }

    public List<BookingComp> getBookingComps() {
        return bookingComps;
    }
}
