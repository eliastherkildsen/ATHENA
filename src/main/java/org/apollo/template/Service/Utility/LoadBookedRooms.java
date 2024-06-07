package org.apollo.template.Service.Utility;

import javafx.scene.layout.VBox;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.ReservedRoomsVBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class LoadBookedRooms {

    public static void loadBookedRooms(ResultSet rs, VBox vbox_booking, List<BookingComp> bookingCompList, BookingSelectionListner listner) throws SQLException {
        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            // Code to extract booking details from the ResultSet
            int bookingId = Integer.parseInt(rs.getString("fld_bookingID"));

            Room room = new Room();
            room.setRoomName(rs.getString("fld_roomName"));

            MeetingType meetingType = new MeetingType(rs.getString("fld_meetingType"));

            String username = rs.getString("fld_userName");
            String start = rs.getString("fld_startTime");
            String end = rs.getString("fld_endTime");

            Time startTime = rs.getTime("fld_startTime");
            Time endTime = rs.getTime("fld_endTime");

            //Creating our information holder object with data from the DB/search
            Booking booking = new Booking();
            booking.setBookingID(bookingId);
            booking.setRoom(room);
            booking.setUsername(username);
            booking.setMeetingType(meetingType);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);

            bookingList.add(booking);
            LoggerMessage.debug("LoadBookedRooms","Booking Found ID : " + booking.getBookingID());
        }

        // Creating our Component using the list of information we generated above.
        ReservedRoomsVBox reservedRoomsVBox = new ReservedRoomsVBox(bookingList);

        // Getting our add action on our components
        for (BookingComp bookingComp : reservedRoomsVBox.getBookingComps()) {
            attachOnAction(bookingComp, bookingCompList, listner);
        }

        // Adding our Vbox of bookings to the container we want them displayed in.
        vbox_booking.getChildren().add(reservedRoomsVBox);
    }

    private static void attachOnAction(BookingComp bookingComp, List<BookingComp> bookingCompList, BookingSelectionListner listner) {
        bookingComp.setOnMouseClicked(mouseEvent -> {
            // unselect all booking comps.
            unselectAllBookingComp(bookingCompList);
            // select the clicked booking comp.
            bookingComp.setBookingCompColor(CompColors.SELECTED);
            // Notify the listener about the selection
            listner.onBookingSelected(bookingComp.getBookingID());
        });
    }

    private static void unselectAllBookingComp(List<BookingComp> bookingCompList) {
        for (BookingComp bookingComp : bookingCompList) {
            bookingComp.setBookingCompColor(CompColors.NORMAL);
        }
    }
}


