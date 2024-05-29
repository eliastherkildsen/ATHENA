package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.AvailableRoom;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAvailableRooms {

    /**
     * This method executes our stored SQL procedure "getAvailableRooms",
     * which finds all rooms with available booking times for today's date.
     * NOTE: We sort the data based on total booking time in our stored procedure.
     * - Rooms are considered available if they are not booked or if the total booking
     * time for today's date is < 480 minutes (8 hours).
     *
     * @param dateToday The today's date for which available rooms are being searched.
     * @return A List of AvailableRoom objects containing available rooms for today's date
     */
    public static List<AvailableRoom> getAvailableRooms(Date dateToday){

        List<AvailableRoom> availableRooms = new ArrayList<>();

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getAvailableRooms @BookingDate = ?");
            ps.setDate(1, dateToday);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                AvailableRoom availableRoom = new AvailableRoom(rs.getInt("fld_roomID"),
                                                                rs.getString("fld_roomName"),
                                                                rs.getString("fld_floor"),
                                                                rs.getString("fld_roomTypeName"),
                                                                rs.getInt("fld_roomMaxPersonCount"));

                availableRooms.add(availableRoom);
            }
            //TODO: right message?
            LoggerMessage.debug("GetAvailableRooms", "Stored Procedure succeeded");
            return availableRooms;

        } catch (SQLException e) {
            LoggerMessage.error("GetAvailableRooms", "Stored Procedure: getAvailableRooms didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
