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

    public static List<Koordinates> getTotalBookingTime(int roomID, Date currentDate){

        List<Koordinates> results = new ArrayList<>();

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getTotalBookingTimePerBooking @roomID = ? , @date = ?");
            ps.setInt(1, roomID);
            ps.setDate(2, currentDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int bookingID = rs.getInt("fld_bookingID");
                int bookingTime = rs.getInt("total_booking_time");

                Koordinates koordinates = new Koordinates(bookingID, bookingTime);
                results.add(koordinates);
            }

            LoggerMessage.info("GetTotBookingTimePerBok", "Stored Procedure succeeded");
            return results;

        } catch (SQLException e) {
            LoggerMessage.error("GetTotBookingTimePerBok", "Stored Procedure: getTotalBookingTimePerBooking didn't run as intended\n" + e.getMessage() + " " + e.getSQLState());
            throw new RuntimeException(e);
        }
    }
}
