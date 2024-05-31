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

public class MeetingTypeDAO implements DAO<MeetingType> {

    // fetching connection from JDBC
    private Connection conn = JDBC.get().getConnection();

    /**
     * Method for adding a new meetingType in to the database.
     * @param meetingType obj.
     */
    @Override
    public void add(MeetingType meetingType) {

        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO tbl_meetingType (fld_meetingType) VALUES ?");
            ps.setString(1, meetingType.getMeetingTypeName()); // replacing the placeholder
            ps.executeQuery();                                          // executing query

            LoggerMessage.info(this, "Inserted, " + meetingType.getMeetingTypeName() + " Into tbl_meetingType");

        // error handling
        } catch (SQLException e) {
            LoggerMessage.error(this, "In add; an error encountered" + e.getMessage());
        }
        // closing prepared statement.
        finally {
            try {
                assert ps != null;
                ps.close();
            } catch (SQLException e) {
                LoggerMessage.error(this, "In add; an error encountered while closing ps" + e.getMessage());

            }
        }

    }

    @Override
    public void delete(MeetingType meetingType) {

        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM tbl_meetingType WHERE fld_meetingType = ?");
            ps.setString(1, meetingType.getMeetingTypeName());  // replacing the placeholder
            ps.executeQuery();                                           // executing quarry

            LoggerMessage.info(this, "In delete; deleted; " + meetingType.getMeetingTypeName());

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
    public void update(MeetingType meetingType) {
        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE tbl_meetingType SET fld_meetingTypeID = ? WHERE fld_meetingType = ?");
            ps.setInt(1, meetingType.getMeetingTypeID());       //replacing the placeholder.
            ps.setString(1, meetingType.getMeetingTypeName());      //replacing the placeholder.
            ps.executeUpdate();                                               //executing quarry
            LoggerMessage.info(this,"In update; updated; " + meetingType.getMeetingTypeName());

        } catch (SQLException e){
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
    public MeetingType read(int id) {
        // establishing preparedStatement
        PreparedStatement ps = null;
        ResultSet rs = null;
        MeetingType meetingType = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_meetingType WHERE fld_meetingTypeID = ?");
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
        return meetingType;

    }

    @Override
    public List<MeetingType> readAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<MeetingType> list = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_meetingType");
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

        return list;

    }
}
