package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetTotBookingTimePerBok {

    public static List<Room> getTotalBookingTime(int roomID, Date currentDate){

        List<Room> availableRooms = new ArrayList<>();

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getTotalBookingTimePerBooking @roomID = ? , @date = ?");
            ps.setInt(1, roomID);
            ps.setDate(2, currentDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int bookingID = rs.getInt("fld_bookingID");
                int bookingTime = rs.getInt("total_booking_time");

            }

            LoggerMessage.info("GetAvailableRooms", "Stored Procedure succeeded");
            return availableRooms;

        } catch (SQLException e) {
            LoggerMessage.error("GetAvailableRooms", "Stored Procedure: getAvailableRooms didn't run as intended\n" + e.getMessage() + " " + e.getSQLState());
            throw new RuntimeException(e);
        }
    }
}
