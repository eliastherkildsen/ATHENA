package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomIDFromName {

    public static int getRoomIDFromName(Room room){
        ResultSet rs;

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC GetRoomIDFromName @roomName = ? ");
            ps.setString(1, room.getRoomName());
            rs = ps.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {

            LoggerMessage.error("getRoomIDFromName", "in onButton_Confirm; An error occurred " + e.getMessage());
        }

        return -1;
    }

}
