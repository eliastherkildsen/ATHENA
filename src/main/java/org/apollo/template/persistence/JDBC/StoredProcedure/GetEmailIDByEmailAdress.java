package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Email;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetEmailIDByEmailAdress {

    public static int getEmailIDByEmailName(Email email){
        ResultSet rs;

        // load all bookings from user email
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC getEmailIDByEmailAdress @EmailAddress = ? ");
            ps.setString(1, email.getEmail());
            rs = ps.executeQuery();
            return rs.getInt("fld_userID");
        } catch (SQLException e) {

            LoggerMessage.error("LoadbookedRoomsByEmail", "in onButton_search; An error occurred " + e.getMessage());
        }

        return -1;
    }

}


