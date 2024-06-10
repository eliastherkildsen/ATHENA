package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Model.MeetingType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeetingTypeDAO extends DAOAbstract<MeetingType> {

    /**
     * Method for adding a new meetingType in to the database.
     * @param meetingType obj.
     */
    @Override
    public void add(MeetingType meetingType) {

        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("INSERT INTO tbl_meetingType (fld_meetingType) VALUES ?");
            ps.setString(1, meetingType.getMeetingTypeName()); // replacing the placeholder
            ps.executeQuery();                                          // executing query

            LoggerMessage.info(this, "Inserted, " + meetingType.getMeetingTypeName() + " Into tbl_meetingType");

        // error handling
        } catch (SQLException e) {
            LoggerMessage.error(this, "In add; an error encountered" + e.getMessage());
        }
        // closing prepared statement.
        finally {
            closeprePareStatement(ps);
        }

    }

    @Override
    public void delete(MeetingType meetingType) {

        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("DELETE FROM tbl_meetingType WHERE fld_meetingType = ?");
            ps.setString(1, meetingType.getMeetingTypeName());  // replacing the placeholder
            ps.executeQuery();                                           // executing quarry

            LoggerMessage.info(this, "In delete; deleted; " + meetingType.getMeetingTypeName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In delete; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }

    }

    @Override
    public void update(MeetingType meetingType) {
        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("UPDATE tbl_meetingType SET fld_meetingTypeID = ? WHERE fld_meetingType = ?");
            ps.setInt(1, meetingType.getMeetingTypeID());       //replacing the placeholder.
            ps.setString(1, meetingType.getMeetingTypeName());      //replacing the placeholder.
            ps.executeUpdate();                                               //executing quarry
            LoggerMessage.info(this,"In update; updated; " + meetingType.getMeetingTypeName());

        } catch (SQLException e){
            LoggerMessage.error(this,"In update; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }

    }

    @Override
    public MeetingType read(int id) {
        // establishing preparedStatement
        PreparedStatement ps = null;
        ResultSet rs = null;
        MeetingType meetingType = new MeetingType();

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_meetingType WHERE fld_meetingTypeID = ?");
            ps.setInt(1, id);

            // executing the quarry and storing it in a resultset.
            rs = ps.executeQuery();

            if (rs.next()) {
                meetingType.setMeetingTypeID(rs.getInt("fld_meetingTypeID"));
                meetingType.setMeetingTypeName(rs.getString("fld_meetingType"));
            }

            LoggerMessage.debug(this,"In read; read; " + meetingType.getMeetingTypeName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In read; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
            closeResultSet(rs);
        }
        return meetingType;

    }

    @Override
    public List<MeetingType> readAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MeetingType> list = new ArrayList<>();

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_meetingType");
            rs = ps.executeQuery();

            while (rs.next()){
                // fetching data.
                int meetingID = rs.getInt("fld_meetingTypeID");
                String meetingType = rs.getString("fld_meetingType");

                // creating obj, and adding it to a list.
                list.add(new MeetingType(meetingType, meetingID));

            }
            LoggerMessage.info(this, "In readAll; list; " + list.size());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In readAll; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
            closeResultSet(rs);
        }

        return list;

    }
}
