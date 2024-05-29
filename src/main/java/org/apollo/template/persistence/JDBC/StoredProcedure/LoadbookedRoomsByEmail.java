package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadbookedRoomsByEmail {

    /**
     * Method for findeing bookings by email. calls method for loading thies,
     * @param email String
     */
    public static ResultSet loadBookedRoomsByEmail(String email) {
        ResultSet rs;

        // load all bookings from user email
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC FindbookingByEmail @EmailAddress = ? ");
            ps.setString(1, email);
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            LoggerMessage.error("LoadbookedRoomsByEmail", "in onButton_search; An error occurred " + e.getMessage());
        }
        return null;
    }

}
