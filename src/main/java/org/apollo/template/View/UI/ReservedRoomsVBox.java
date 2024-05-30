package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.BookingInformationSimple;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ReservedRoomsVBox extends VBox {

    List<BookingComp> bookingComps = new ArrayList<>();
    /**
     * Constructor to create a VBox displaying reservation information.
     * @param BookingInformationSimple List of BookingInformationSimple objects containing booking information.
     */
    public ReservedRoomsVBox(List<BookingInformationSimple> BookingInformationSimple) {
        // Set the background color of the VBox to transparent blue
        this.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        if (BookingInformationSimple == null){

            LoggerMessage.warning(this,"BookingInformation Array Cannot be NULL");

        } else {

            // Set alignment and padding for the VBox
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(10));

            LoggerMessage.info(this, "Attempting to generate VBox with Bookings.");

            for (BookingInformationSimple i : BookingInformationSimple) {
                BookingComp bookingComponent;
                LoggerMessage.trace(this, i.getRoomName() + " | " + i.getUserName() + " | Adding to vbox");

                //Ensures that the String Start time and end appears as HH:MM - HH:MM
                StringBuilder startEndTime = new StringBuilder();
                startEndTime.append(i.getStartTime().substring(0, 5));
                startEndTime.append(" - ");
                startEndTime.append(i.getEndTime().substring(0, 5));
                String time = startEndTime.toString();

                if (i.getBookingID() == -1){
                    LoggerMessage.debug(this,"Making BookingComp without ID");
                    bookingComponent = new BookingComp(i.getRoomName(),i.getUserName(),i.getMeetingType(),time);
                } else {
                    LoggerMessage.debug(this,"Making BookingComp with ID: " + i.getBookingID());
                    bookingComponent = new BookingComp(i.getRoomName(),i.getUserName(),i.getMeetingType(),time,i.getBookingID());
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
