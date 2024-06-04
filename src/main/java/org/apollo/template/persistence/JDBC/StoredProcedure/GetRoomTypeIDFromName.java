package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.RoomType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetRoomTypeIDFromName {

    public static RoomType getRoomTypeIDFromName(String roomTypeName){
        PreparedStatement ps = null;
        RoomType roomType = new RoomType();

        try {
            ps = JDBC.get().getConnection().prepareStatement("EXECUTE getRoomTypeIDFromName @RoomTypeName = ?");
            ps.setString(1, roomTypeName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                roomType.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                roomType.setRoomTypeName(roomTypeName);
                roomType.setRoomTypeDescription(rs.getString("fld_roomTypeDescription"));
            }

            return roomType;

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
