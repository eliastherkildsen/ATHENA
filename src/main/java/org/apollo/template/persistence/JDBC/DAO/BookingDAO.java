package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.*;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements DAO<Booking> {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(Booking booking) {
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
            ps.setTime(1, booking.getStartTime());
            ps.setTime(2, booking.getEndTime());
            ps.setDate(3, Date.valueOf(booking.getDate()));
            ps.setBoolean(4, false); // due to this being an ad-hog or standard booking catering is disabled.
            ps.setInt(5, booking.getNumberOfParticipants());
            ps.setString(6, booking.getUsername());
            ps.setInt(7, booking.getEmail().getEmailID());
            ps.setInt(8, booking.getRoom().getRoomId());
            ps.setInt(9, booking.getMeetingType().getMeetingTypeID());
            ps.setInt(10, 1); // due to this being an ad-hog or standard department type is ad-hog / id 1
            ps.setInt(11, 1); // due to this being an ad-hog or standard team type is ad-hog / id 1
            ps.executeUpdate();
            LoggerMessage.info(this, "In add; added; " + booking.getBookingID());


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
    public void delete(Booking booking) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM tbl_booking WHERE fld_bookingID = ?");
            ps.setInt(1, booking.getBookingID());
            ps.executeUpdate();
            LoggerMessage.info(this, "In delete; deleted; " + booking.getBookingID());

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
    public void update(Booking booking) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE tbl_booking SET " +
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
            ps.setTime(1, booking.getStartTime());
            ps.setTime(2, booking.getEndTime());
            ps.setDate(3, Date.valueOf(booking.getDate()));
            ps.setBoolean(4, false); // due to this being an ad-hog or standard booking catering is disabled.
            ps.setInt(5, booking.getNumberOfParticipants());
            ps.setString(6, booking.getUsername());
            ps.setInt(7, booking.getEmail().getEmailID());
            ps.setInt(8, booking.getRoom().getRoomId());
            ps.setInt(9, booking.getMeetingType().getMeetingTypeID());
            ps.setInt(10, 1); // due to this being an ad-hog or standard department type is ad-hog / id 1
            ps.setInt(11, 1); // due to this being an ad-hog or standard team type is ad-hog / id 1
            ps.executeUpdate();

            LoggerMessage.info(this,"In update; updated; " + booking.getBookingID());

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
    public Booking read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Booking booking = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_booking WHERE fld_bookingID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                // building booking with builder pattern.
                booking = new Booking()
                        .setBookingID(rs.getInt("fld_bookingID"))
                        .setNumberOfParticipants(rs.getInt("fld_numberOfParticipants"))
                        .setUsername(rs.getString("fld_userName"))
                        .setRoom(new Room(rs.getInt("fld_roomID")))
                        .setEmail(new Email(rs.getInt("fld_userID")))
                        .setMeetingType(new MeetingType(rs.getInt("fld_meetingTypeID")))
                        .setDate(rs.getDate("fld_date").toLocalDate())
                        .setStartTime(rs.getTime("fld_startTime"))
                        .setEndTime(rs.getTime("fld_endTime"));

            }
            assert booking != null;
            LoggerMessage.debug(this,"In read; read; " + booking.getBookingID());
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
        return booking;
    }

    @Override
    public List<Booking> readAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Booking> bookingList = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_booking");
            rs = ps.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking()
                        .setBookingID(rs.getInt("fld_bookingID"))
                        .setNumberOfParticipants(rs.getInt("fld_numberOfParticipants"))
                        .setUsername(rs.getString("fld_userName"))
                        .setRoom(new Room(rs.getInt("fld_roomID")))
                        .setEmail(new Email(rs.getInt("fld_userID")))
                        .setMeetingType(new MeetingType(rs.getInt("fld_meetingTypeID")))
                        .setDate(rs.getDate("fld_date").toLocalDate())
                        .setStartTime(rs.getTime("fld_startTime"))
                        .setEndTime(rs.getTime("fld_endTime"));

                bookingList.add(booking);

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

