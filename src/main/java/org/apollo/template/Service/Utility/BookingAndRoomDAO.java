package org.apollo.template.Service.Utility;

import org.apollo.template.Model.Booking;
import org.apollo.template.Model.Room;
import org.apollo.template.persistence.JDBC.DAO.BookingDAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.util.ArrayList;
import java.util.List;

public class BookingAndRoomDAO {

    public static List<Booking> combinBookingAndRoom(){
        BookingDAO bookingDAO = new BookingDAO();
        RoomDAO roomDAO = new RoomDAO();

        List<Room> roomList = new ArrayList<>();
        List<Booking> bookingsList = new ArrayList();

        bookingsList = bookingDAO.readAll();
        roomList = roomDAO.readAll();

        for (Booking booking : bookingsList) {
            for (Room room : roomList) {
                if (booking.getRoom().getRoomID() == room.getRoomID()) {
                    booking.setRoom(room);
                }
            }
        }

        return bookingsList;
    }
}
