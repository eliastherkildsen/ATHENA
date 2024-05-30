package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDAO implements DAO<RoomType>{

    Connection conn = JDBC.get().getConnection();

    @Override
    public void add(RoomType roomType) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbl_roomType (fld_roomTypeName, fld_roomTypeDescription) VALUES ?, ?");
            ps.setString(1, roomType.getRoomTypeName());
            ps.setString(2, roomType.getRoomTypeDescription());
            ps.executeQuery();
            LoggerMessage.info(this, "In add; added; " + roomType.getRoomTypeName());
        } catch (SQLException e) {
            LoggerMessage.error(this,"In add; An error occurred " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In add; Error occurred during closing of PreparedStatement : " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void delete(RoomType roomType) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM tbl_roomType WHERE fld_roomTypeID = ?");
            ps.setInt(1, roomType.getRoomTypeID());
            ps.executeQuery();
            LoggerMessage.info(this, "In delete; deleted; " + roomType.getRoomTypeName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In delete; An error occurred " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In delete; Error occurred during closing of PreparedStatement : " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void update(RoomType roomType) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE tbl_roomType SET fld_roomTypeName = ?, fld_roomTypeDescription = ?");
            ps.setString(1, roomType.getRoomTypeName());
            ps.setString(2, roomType.getRoomTypeDescription());
            ps.executeUpdate();
            LoggerMessage.info(this, "In update; updated; " + roomType.getRoomTypeName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In update; An error occurred " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In update; Error occurred during closing of PreparedStatement : " + e.getMessage());
                }
            }
        }
    }

    @Override
    public RoomType read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        RoomType roomType = new RoomType();

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_roomType WHERE fld_roomTypeID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()){
                roomType.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                roomType.setRoomTypeName(rs.getString("fld_roomTypeName"));
                roomType.setRoomTypeDescription(rs.getString("fld_roomTypeDescription"));
            }
            LoggerMessage.debug(this, "In read; read; " + roomType.getRoomTypeName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In read; An error occurred " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In read; Error occurred during closing of PreparedStatement : " + e.getMessage());
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In read; Error occurred during closing of ResultSet : " + e.getMessage());
                }
            }
        }


        return roomType;
    }

    @Override
    public List<RoomType> readAll() {
    List<RoomType> roomTypeList = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        ps = conn.prepareStatement("SELECT * FROM tbl_roomType");
        rs = ps.executeQuery();

        while (rs.next()){
            RoomType roomType = new RoomType();
            roomType.setRoomTypeID(rs.getInt("fld_roomTypeID"));
            roomType.setRoomTypeName(rs.getString("fld_roomTypeName"));
            roomType.setRoomTypeDescription(rs.getString("fld_roomTypeDescription"));
            roomTypeList.add(roomType);
        }
        LoggerMessage.info(this, "In read all; list; " + roomTypeList.size());

    } catch (SQLException e) {
        LoggerMessage.error(this,"In readAll; An error occurred " + e.getMessage());
    } finally {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                LoggerMessage.error(this,"In readAll; Error occurred during closing of PreparedStatement : " + e.getMessage());
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LoggerMessage.error(this,"In readAll; Error occurred during closing of ResultSet : " + e.getMessage());
            }
        }
    }
    return roomTypeList;
    }
}
