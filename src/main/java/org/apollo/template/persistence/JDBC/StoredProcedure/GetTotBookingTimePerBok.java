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

        System.out.println("RoomID: " + roomID);
        System.out.println("CurrentDate: " + currentDate);

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getTotalBookingTimePerBooking @roomID = ? , @date = ?");
            ps.setInt(1, roomID);
            ps.setDate(2, currentDate);
            ResultSet rs = ps.executeQuery();

            System.out.println("HER");

            while (rs.next()) {

                System.out.println("også her?");

                int bookingID = rs.getInt("fld_bookingID");
                int bookingTime = rs.getInt("total_booking_time");

                System.out.println("BookingID: " + bookingID);
                System.out.println("BookingTime: " + bookingTime);

                Koordinates koordinates = new Koordinates(String.valueOf(bookingID), bookingTime);
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
