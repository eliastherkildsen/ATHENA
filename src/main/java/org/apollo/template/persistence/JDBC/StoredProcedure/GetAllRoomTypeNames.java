package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllRoomTypeNames {

    public static List<String> getAllRoomTypeNames(){
        PreparedStatement ps = null;
        List<String> nameList = new ArrayList<>();
        try {
            ps = JDBC.get().getConnection().prepareStatement("EXECUTE getAllRoomTypeNames");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                nameList.add(rs.getString("fld_roomTypeName"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return nameList;
    }

}
