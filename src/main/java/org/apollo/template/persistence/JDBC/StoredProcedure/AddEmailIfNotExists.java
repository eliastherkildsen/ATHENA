package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Email;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddEmailIfNotExists {

    /**
     * Method for adding a email to the database, checks if it already exists before adding it.
     * @param email Email
     */
    public static void addEmailIfNotExists(Email email) {
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC AddEmailIfNotExists @EmailAddress = ?");
            ps.setString(1, email.getEmail());
            ResultSet rs = ps.executeQuery();
            LoggerMessage.debug("AddEmailIfNotExists", "JDBC: " + rs.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
