package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.BookingsByDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetBookingsByDate {

    public static List<Booking> getBookingsByDate(Date dateToday){
        List<Booking> bookingList = new ArrayList<>();
        LoggerMessage.debug(BookingsByDate.class, "Todays date: " + dateToday);

        /**
         * Running our stored SQL PROCEDURE
         * NOTE: We are sorting the data we get by time in the stored PROCEDURE
         * Returns:
         *    SELECT
         *         tbl_booking.fld_startTime,
         *         tbl_booking.fld_endTime,
         *         tbl_booking.fld_userName,
         *         tbl_room.fld_roomName,
         * 		   tbl_meetingType.fld_meetingType
         */
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC GetBookingsByDate @BookingDate = ?");
            ps.setDate(1, dateToday);
            ResultSet rs = ps.executeQuery();
            //No result?
            if (!rs.next()){
                LoggerMessage.info(BookingsByDate.class, "No results found for " + dateToday);
            } else {
                //Else lets do stuff with the result - Do while to ensure all results are included.
                //Do-while to ensure we include the first result from our resultset.
                do {
                    Booking booking = new Booking();
                    Room room = new Room();
                    room.setRoomName(rs.getString("fld_roomName"));
                    Time startTime = rs.getTime("fld_startTime");
                    Time endTime = rs.getTime("fld_endTime");
                    String username = rs.getString("fld_username");
                    MeetingType meetingType = new MeetingType(rs.getString("fld_meetingType"));


                    booking.setStartTime(startTime);
                    booking.setEndTime(endTime);
                    booking.setUsername(username);
                    booking.setRoom(room);
                    booking.setMeetingType(meetingType);

                    bookingList.add(booking);
                    LoggerMessage.debug(BookingsByDate.class, "Size of ArrayList : " + bookingList.size());
                    LoggerMessage.info(BookingsByDate.class,"Arraylist Created.");
                } while (rs.next());

                return bookingList;
            }

        } catch (SQLException e) {
            LoggerMessage.warning(BookingsByDate.class,"Have you installed, ");
            LoggerMessage.error(BookingsByDate.class,"Stored Procedure : GetBookingsByDate didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
