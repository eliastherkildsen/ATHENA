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

public class RoomDAO extends DAOAbstract<Room> {


    @Override
    public void add(Room room) {
        PreparedStatement ps = null;
        try {
            ps = getCONN().prepareStatement("INSERT INTO tbl_room (fld_roomName, fld_roomMaxPersonCount, fld_roomTypeID, fld_floor) VALUES (?, ?, ?, ?)");
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getRoomMaxPersonCount());
            ps.setInt(3, room.getRoomTypeID());
            ps.setInt(4, room.getFloor());
            ps.executeUpdate();
            LoggerMessage.info(this, "In add; added; " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this, "In add; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }
    }




    @Override
    public void delete(Room room) {
        PreparedStatement ps = null;
        try {
            ps = getCONN().prepareStatement("DELETE FROM tbl_room WHERE fld_roomID = ?");
            ps.setInt(1, room.getRoomID());
            ps.executeUpdate();
            LoggerMessage.info(this, "In delete; deleted; " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In delete; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }
    }


    @Override
    public void update(Room room) {
        PreparedStatement ps = null;
        try {
            ps = getCONN().prepareStatement("UPDATE tbl_room SET fld_roomName = ?, fld_roomMaxPersonCount = ?, fld_roomTypeID = ?, fld_floor = ? WHERE fld_roomID = ?");
            ps.setString(1, room.getRoomName());
            ps.setInt(2, room.getRoomMaxPersonCount());
            ps.setInt(3, room.getRoomTypeID());
            ps.setInt(4, room.getFloor());
            ps.setInt(5, room.getRoomID());
            ps.executeUpdate();
            LoggerMessage.info(this,"In update; updated; " + room.getRoomName());

        } catch (SQLException e){
            LoggerMessage.error(this,"In update; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }
    }



    @Override
    public Room read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Room room = new Room();

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_room WHERE fld_roomID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                room.setRoomID(rs.getInt("fld_roomID"));
                room.setRoomName(rs.getString("fld_roomName"));
                room.setRoomMaxPersonCount(rs.getInt("fld_roomMaxPersonCount"));
                room.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                room.setFloor(rs.getInt("fld_floor"));
            }
            LoggerMessage.debug(this,"In read; read; " + room.getRoomName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In read; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
            closeResultSet(rs);
        }
        return room;
    }

    @Override
    public List<Room> readAll() {
        List<Room> roomList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_room");
            rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("fld_roomID"));
                room.setRoomName(rs.getString("fld_roomName"));
                room.setRoomMaxPersonCount(rs.getInt("fld_roomMaxPersonCount"));
                room.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                room.setFloor(rs.getInt("fld_floor"));
                roomList.add(room);
            }
            LoggerMessage.info(this, "In readAll; list; " + roomList.size());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In readAll; An error occurred " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeprePareStatement(ps);
        }
        return roomList;
    }
}
