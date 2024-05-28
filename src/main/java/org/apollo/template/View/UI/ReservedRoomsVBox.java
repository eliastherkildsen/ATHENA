package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.util.ArrayList;
import java.util.List;

public class ReservedRoomsVBox extends VBox {
    List rooms = new ArrayList();

    /**
     *
     * @param Bookinginformation
     */
    public ReservedRoomsVBox(List<BookingInformation> Bookinginformation) {

        if (Bookinginformation == null){

            LoggerMessage.warning(this,"BookingInformation Array Cannot be NULL");

        } else {

            LoggerMessage.info(this, "Attempting to generate VBox with Bookings.");

            for (BookingInformation i : Bookinginformation) {
                LoggerMessage.trace(i.getRoomName() + " | " + i.getBookingId() + " | " + i.getUserName(), "Adding to vbox");
                StringBuilder startEndTime = new StringBuilder();
                startEndTime.append(i.getStartTime());
                startEndTime.append(" - ");
                startEndTime.append(i.getEndTime());
                String time = startEndTime.toString();

                BookingComp book = new BookingComp(i.getRoomName(), i.getUserName(), i.getBookingId(), time); //TODO Needs to find the correct Booking from BookingID

                this.setAlignment(Pos.CENTER);
                this.setMargin(book, new Insets(10));
                this.getChildren().add(book);
            }
        }
    }


}
