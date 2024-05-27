package org.apollo.template.View.UI;

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
        }

        for (BookingInformation i : Bookinginformation) {
            LoggerMessage.info(this, "Adding to vbox");
            if (i != null) {
                StringBuilder startEndTime = new StringBuilder();
                startEndTime.append(i.getStartTime());
                startEndTime.append(" - ");
                startEndTime.append(i.getEndTime());
                String time = startEndTime.toString();

                BookingComp book = new BookingComp(i.getRoomName(), i.getUserName(), i.getBookingName(), time);
                this.getChildren().add(book);
            }
        }
    }


}
