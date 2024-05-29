package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.ReservedRoomDate;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ReservedRoomsVBox extends VBox {

    /**
     * Constructor to create a VBox displaying reservation information.
     * @param ReservedRoomDate List of ReservedRoomDate objects containing booking information.
     */
    public ReservedRoomsVBox(List<ReservedRoomDate> ReservedRoomDate) {
        // Set the background color of the VBox to transparent blue
        this.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        if (ReservedRoomDate == null){

            LoggerMessage.warning(this,"BookingInformation Array Cannot be NULL");

        } else {

            // Set alignment and padding for the VBox
            this.setAlignment(Pos.CENTER);
            this.setPadding(new Insets(10));

            LoggerMessage.info(this, "Attempting to generate VBox with Bookings.");

            for (ReservedRoomDate i : ReservedRoomDate) {
                LoggerMessage.trace(this, i.getRoomName() + " | " + i.getUserName() + " | Adding to vbox");
                StringBuilder startEndTime = new StringBuilder();
                startEndTime.append(i.getStartTime().substring(0, 5));
                startEndTime.append(" - ");
                startEndTime.append(i.getEndTime().substring(0, 5));
                String time = startEndTime.toString();

                BookingComp bookingComponent = new BookingComp(i.getRoomName(),i.getUserName(),i.getMeetingType(),time);

                // Set margin for each booking component
                this.setMargin(bookingComponent, new Insets(10));
                this.getChildren().add(bookingComponent);
            }
        }

    }


}
