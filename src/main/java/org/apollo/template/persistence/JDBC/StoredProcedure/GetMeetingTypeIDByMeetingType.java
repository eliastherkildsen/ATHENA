package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMeetingTypeIDByMeetingType {

    public static int getMeetingTypeIDByMeetingType(MeetingType meetingType){
        ResultSet rs;

        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC getMeetingTypeIDByMeetingType @meetingType = ? ");
            ps.setString(1, meetingType.getMeetingTypeName());
            rs = ps.executeQuery();
            while (rs.next()){
                return rs.getInt("fld_meetingTypeID");
            }
        } catch (SQLException e) {

            LoggerMessage.error("LoadbookedRoomsByEmail", "in onButton_search; An error occurred " + e.getMessage());
        }

        return -1;
    }
}
