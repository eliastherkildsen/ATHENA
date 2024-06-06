package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Statistics.Koordinates;
import org.apollo.template.Service.Logger.LoggerMessage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetTotBookingTimePerBok {

    /**
     * This method retrieves the total booking time per booking for a given room and date using a stored procedure
     * @param roomID the roomID for a selected room
     * @param currentDate today's date
     * @return a List af Koordinates objects containing bookings and total booking time
     */
    public static List<Koordinates> getTotalBookingTime(int roomID, Date currentDate){

        List<Koordinates> results = new ArrayList<>();

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getTotalBookingTimePerBooking @roomID = ? , @date = ?");
            ps.setInt(1, roomID);
            ps.setDate(2, currentDate);
            ResultSet rs = ps.executeQuery();

            // counter to name x-value
            int count = 1;

            while (rs.next()) {

                int bookingID = rs.getInt("fld_bookingID");
                int bookingTime = rs.getInt("total_booking_time");

                Koordinates koordinates = new Koordinates(String.format("%d. Booking", count), bookingTime);
                results.add(koordinates);

                count++;
            }

            LoggerMessage.info("GetTotBookingTimePerBok", "Stored Procedure succeeded");
            return results;

        } catch (SQLException e) {
            LoggerMessage.error("GetTotBookingTimePerBok", "Stored Procedure: getTotalBookingTimePerBooking didn't run as intended\n" + e.getMessage() + " " + e.getSQLState());
            throw new RuntimeException(e);
        }
    }
}
