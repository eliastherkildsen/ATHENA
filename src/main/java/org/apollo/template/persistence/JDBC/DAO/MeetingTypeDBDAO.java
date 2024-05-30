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

public class MeetingTypeDBDAO implements DAO<MeetingType> {

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
            ps.setString(1, meetingType.getMeetingType()); // replacing the placeholder
            ps.executeQuery();                                          // executing query

            LoggerMessage.info(this, "Inserted, " + meetingType.getMeetingType() + " Into tbl_meetingType");

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
            ps.setString(1, meetingType.getMeetingType());  // replacing the placeholder
            ps.executeQuery();                                           // executing quarry

        // error handling
        } catch (SQLException e) {
            LoggerMessage.error(this, "In delete, an error encountered " + e.getMessage());
        }

        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In delete, an error encountered while closing ps" + e.getMessage());
                }
            }
        }

    }

    @Override
    public void update(MeetingType meetingType) {
        // establishing preparedStatement
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE FROM tbl_meetingType SET fld_meetingTypeID = ? WHERE fld_meetingType = ?");
            ps.setInt(1, meetingType.getMeetingTypeID());       //replacing the placeholder.
            ps.setString(1, meetingType.getMeetingType());      //replacing the placeholder.
            ps.executeQuery();                                               //executing quarry

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN update; An error encountered " + e.getMessage());
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In update, an error encountered while closing ps" + e.getMessage());
                }
            }
        }

    }

    @Override
    public MeetingType read(int id) {
        // establishing preparedStatement
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_meetingType WHERE fld_meetingTypeID = ?");
            ps.setInt(1, id);

            // executing the quarry and storing it in a resultset.
            rs = ps.executeQuery();

            int meetingID = rs.getInt("fld_meetingTypeID");
            String meetingType = rs.getString("fld_meetingType");

            return new MeetingType(meetingType, meetingID);

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN read; An error encountered " + e.getMessage());
        }

        finally {
            // closing the prepared statement.
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In read, an error encountered while closing ps" + e.getMessage());

                }
            }

            // closing the resultset.
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In read, an error encountered while closing rs" + e.getMessage());

                }
            }

        }

        return null; // defuald return if an error is encountered.

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


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN readall; An error encountered " + e.getMessage());
        } finally {

            // closing the PreparedStatement statement.
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In readAll, an error encountered while closing ps" + e.getMessage());

                }
            }

            // closing the resultset.
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this, "In readAll, an error encountered while closing rs" + e.getMessage());

                }
            }
        }

        return list;

    }
}
