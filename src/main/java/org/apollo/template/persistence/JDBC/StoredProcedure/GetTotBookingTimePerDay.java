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

public class GetTotBookingTimePerDay {

    /**
     * This method retrieves the total booking time per day for a given room and date using a stored procedure
     *
     * @param roomID      the roomID for a selected room
     * @param startDate   the start date from which to retrieve total booking time per day
     * @param currentDate today's date
     * @return a List af Koordinates objects containing date and total booking time
     */
    public static List<Koordinates> getTotalBookingDay(int roomID, Date startDate, Date currentDate) {

        // establishing preparedStatement
        PreparedStatement ps = null;
        List<Koordinates> results = new ArrayList<>();

        try {
            ps = JDBC.get().getConnection().prepareStatement("EXECUTE getTotalBookingTimePerDay @roomID = ? , @startDate = ?, @endDate = ?");
            ps.setInt(1, roomID);
            ps.setDate(2, startDate);
            ps.setDate(3, currentDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Date date = rs.getDate("fld_date");
                int bookingTime = rs.getInt("total_booking_time");

                Koordinates koordinates = new Koordinates(String.valueOf(date), bookingTime);
                results.add(koordinates);
            }

            LoggerMessage.info("GetTotBookingTimePerBok", "Stored Procedure succeeded");
            return results;

        } catch (SQLException e) {
            LoggerMessage.error("GetTotBookingTimePerBok", "Stored Procedure: getTotalBookingTimePerBooking didn't run as intended\n" + e.getMessage());
            // closing prepared statement
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                LoggerMessage.error("GetTotBookingTimerPerDay", "Stored Procedure: an error encountered while closing ps" + e.getMessage());
            }
        }
        return results;
    }
}

