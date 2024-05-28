package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.ReservedRoomDate;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.util.ArrayList;
import java.util.List;

public class ReservedRoomsVBox extends VBox {
    List rooms = new ArrayList();

    /**
     *
     * @param Bookinginformation
     */
    public ReservedRoomsVBox(List<ReservedRoomDate> ReservedRoomDate) {

        if (ReservedRoomDate == null){

            LoggerMessage.warning(this,"BookingInformation Array Cannot be NULL");

        } else {

            LoggerMessage.info(this, "Attempting to generate VBox with Bookings.");

            for (ReservedRoomDate i : ReservedRoomDate) {
                LoggerMessage.trace(this, i.getRoomName() + " | " + i.getUserName() + " | Adding to vbox");
                StringBuilder startEndTime = new StringBuilder();
                startEndTime.append(i.getStartTime().substring(0, 5));
                startEndTime.append(" - ");
                startEndTime.append(i.getEndTime().substring(0, 5));
                String time = startEndTime.toString();

                BookingComp book = new BookingComp(i.getRoomName(),i.getUserName(),i.getMeetingType(),time);

                this.setAlignment(Pos.CENTER);
                this.setMargin(book, new Insets(10));
                this.getChildren().add(book);
            }
        }
    }


}
