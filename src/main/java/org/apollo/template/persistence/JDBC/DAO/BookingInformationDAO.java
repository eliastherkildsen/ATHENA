package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingInformationDAO implements DAO<BookingInformation> {

    // JDBC Connection
    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(BookingInformation bookingInformation) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO tbl_booking (" +
                    "fld_startTime, " +
                    "fld_endTime, " +
                    "fld_date, " +
                    "fld_catering, " +
                    "fld_numberOfParticipants, " +
                    "fld_userName, " +
                    "fld_userID, " +
                    "fld_roomID, " +
                    "fld_meetingTypeID, " +
                    "fld_departmentID, " +
                    "fld_teamID" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, bookingInformation.getStartTime());
            ps.setString(2, bookingInformation.getEndTime());
            ps.setDate(3, bookingInformation.getDate());
            ps.setInt(4, bookingInformation.isCateringBit());
            ps.setInt(5, bookingInformation.getNumberOfParticipants());
            ps.setString(6, bookingInformation.getUserName());
            ps.setInt(7, bookingInformation.getUserID());
            ps.setInt(8, bookingInformation.getRoomID());
            ps.setInt(9, bookingInformation.getMeetingTypeID());
            ps.setInt(10, bookingInformation.getDepartmentID());
            ps.setInt(11, bookingInformation.getTeamID());
            ps.executeUpdate();
            LoggerMessage.info(this, "In add; added; " + bookingInformation.getBookingID());


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
    public void delete(BookingInformation bookingInformation) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbl_booking WHERE fld_bookingID = ?");
            ps.setInt(1, bookingInformation.getBookingID());
            ps.executeUpdate();
            LoggerMessage.info(this, "In delete; deleted; " + bookingInformation.getBookingID());

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
    public void update(BookingInformation bookingInformation) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbl_booking SET " +
                    "fld_startTime = ?, " +
                    "fld_endTime = ?, " +
                    "fld_date = ?, " +
                    "fld_catering = ?, " +
                    "fld_numberOfParticipants = ?, " +
                    "fld_userName = ?, " +
                    "fld_userID = ?, " +
                    "fld_roomID = ?, " +
                    "fld_meetingTypeID = ?, " +
                    "fld_departmentID = ?, " +
                    "fld_teamID = ? " +
                    "WHERE fld_bookingID = ?");
            ps.setString(1, bookingInformation.getStartTime());
            ps.setString(2, bookingInformation.getEndTime());
            ps.setDate(3, bookingInformation.getDate());
            ps.setInt(4, bookingInformation.isCateringBit());
            ps.setInt(5, bookingInformation.getNumberOfParticipants());
            ps.setString(6, bookingInformation.getUserName());
            ps.setInt(7, bookingInformation.getUserID());
            ps.setInt(8, bookingInformation.getRoomID());
            ps.setInt(9, bookingInformation.getMeetingTypeID());
            ps.setInt(10, bookingInformation.getDepartmentID());
            ps.setInt(11, bookingInformation.getTeamID());
            ps.setInt(12, bookingInformation.getBookingID());
            ps.executeUpdate();
            LoggerMessage.info(this,"In update; updated; " + bookingInformation.getBookingID());

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
    public BookingInformation read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookingInformation bookingInformation = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_booking WHERE fld_bookingID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                bookingInformation = new BookingInformation(
                        rs.getInt("fld_bookingID"),
                        rs.getInt("fld_catering"),
                        rs.getInt("fld_numberOfParticipants"),
                        rs.getInt("fld_roomID"),
                        rs.getInt("fld_teamID"),
                        rs.getInt("fld_userID"),
                        rs.getInt("fld_meetingTypeID"),
                        rs.getInt("fld_departmentID"),
                        rs.getString("fld_userName"),
                        rs.getString("fld_startTime"),
                        rs.getString("fld_endTime"),
                        rs.getDate("fld_date")
                );
            }
            LoggerMessage.debug(this,"In read; read; " + bookingInformation.getBookingID());
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
        return bookingInformation;
    }

    @Override
    public List<BookingInformation> readAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<BookingInformation> bookingList = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_booking");
            rs = ps.executeQuery();
            while (rs.next()) {
                BookingInformation bookingInformation = new BookingInformation(
                        rs.getInt("fld_bookingID"),
                        rs.getInt("fld_catering"),
                        rs.getInt("fld_numberOfParticipants"),
                        rs.getInt("fld_roomID"),
                        rs.getInt("fld_teamID"),
                        rs.getInt("fld_userID"),
                        rs.getInt("fld_meetingTypeID"),
                        rs.getInt("fld_departmentID"),
                        rs.getString("fld_userName"),
                        rs.getString("fld_startTime"),
                        rs.getString("fld_endTime"),
                        rs.getDate("fld_date")
                );
                bookingList.add(bookingInformation);
                LoggerMessage.info(this, "In readAll; list; " + bookingList.size());
            }
        } catch (SQLException e) {
            LoggerMessage.error(this, "In readAll; an error encountered: " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    LoggerMessage.error(this,"In readAll; Error occurred during closing of PreparedStatement : \n" + e.getMessage());
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
        return bookingList;
    }
}
