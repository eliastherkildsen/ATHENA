package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RoomDAODB implements DAO<Room> {

    Connection conn = JDBC.get().getConnection();

    @Override
    public void add(Room room) {

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tbl_room (fld_roomName, fld_roomMaxPersonCount, fld_roomTypeID, fld_floor) VALUES ?, ?, ?, ?");
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getRoomMaxPersonCount());
            ps.setInt(3, room.getRoomTypeID());
            ps.setInt(4, room.getFloor());
            ps.executeQuery();
            ps.close();
            LoggerMessage.info(this, "In add; Added room: " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN add; An error occurred " + e.getMessage());
        }


    }


    @Override
    public void delete(Room room) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM tbl_room WHERE fld_roomName = ?");
            ps.setString(1, room.getRoomName());
            ps.executeQuery();
            ps.close();
            LoggerMessage.info(this, "In Delete; deleted room: " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN delete; An error occurred " + e.getMessage());
        }
    }


    @Override
    public void update(Room room) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tbl_room SET fld_roomName = ?, " +
                    "fld_roomMaxPersonCount = ?, fld_floor = ?, fld_roomTypeID = ? WHERE fld_roomID = ?");

            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getRoomMaxPersonCount());
            ps.setInt(3, room.getFloor());
            ps.setInt(4, room.getRoomTypeID());
            ps.setInt(5, room.getRoomId());

            ps.executeQuery();
            ps.close();
            LoggerMessage.info(this, "In update; updated room: " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN update; An error occurred " + e.getMessage());
        }
    }



    @Override
    public Room read(int id) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tbl_room WHERE fld_room = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            int roomID = rs.getInt("fld_roomID");
            String roomName = rs.getString("fld_roomName");
            int roomMaxCap = rs.getInt("fld_roomMaxPersonCount");
            int roomTypeID = rs.getInt("fld_roomTypeID");
            int floor = rs.getInt("fld_floor");

            ps.close();
            rs.close();

            return new Room(roomID, roomMaxCap, roomTypeID, floor, roomName);


        } catch (SQLException e) {
            LoggerMessage.error(this, "In read; an error occurred " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<Room> readAll() {

        List<Room> roomList = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tbl_room");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int roomID = rs.getInt("fld_roomID");
                String roomName = rs.getString("fld_roomName");
                int roomMaxCap = rs.getInt("fld_roomMaxPersonCount");
                int roomTypeID = rs.getInt("fld_roomTypeID");
                int floor = rs.getInt("fld_floor");

                roomList.add(new Room(roomID, roomMaxCap, roomTypeID, floor, roomName));

            }
            ps.close();
            rs.close();

        } catch (SQLException e) {
            LoggerMessage.error(this, "In read; an error occurred " + e.getMessage());
        }

        return roomList;
    }
}
