package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErrorReportDAODB implements DAO<ErrorReport> {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(ErrorReport errorReport) {

        try {
            PreparedStatement ps =  conn.prepareStatement("INSERT INTO tbl_errorReport (fld_archived, fld_reportDate," +
                    " fld_inventoryID, fld_userID, fld_reportDescription, fld_roomID) VALUES (?, ?, ?, ?, ?, ?)");

            // these conversions is done due to MSSQL only accepting a bit and not a boolean.
            if (errorReport.isArchived()){
                ps.setInt(1, 1);
            } else {
                ps.setInt(1, 0);
            }

            // replacing sql place holders
            ps.setDate(2, Date.valueOf(errorReport.getReportDate()));
            ps.setInt(3, errorReport.getInventoryItems().getId());
            ps.setInt(4, errorReport.getEmail().getEmailID());
            ps.setString(5, errorReport.getErrorReportDescription());
            ps.setInt(6, errorReport.getRoom().getRoomID());
            ps.executeUpdate();

            // closing the prepared statement.
            ps.close();

            LoggerMessage.info(this, "in add; added new error report : " + errorReport);
        } catch (SQLException e) {
            LoggerMessage.error(this, "in add: an error occurred " + e.getMessage() + Arrays.toString(e.getStackTrace()));
        }

    }


    @Override
    public void delete(ErrorReport errorReport) {
        try {
            PreparedStatement ps =  conn.prepareStatement("DELETE FROM tbl_errorReport WHERE fld_errorReportID = ?");
            ps.setInt(1, errorReport.getErrorReportID());
            ps.executeUpdate();

            // closing the prepared statement.
            ps.close();

            LoggerMessage.info(this, "in delete; removed error report : " + errorReport);
        } catch (SQLException e) {
            LoggerMessage.error(this, "in delete: an error occurred " + e.getMessage());
        }
    }

    @Override
    public void update(ErrorReport errorReport) {
        try {
            PreparedStatement ps =  conn.prepareStatement("UPDATE tbl_errorReport SET fld_archived = ?, " +
                    "fld_reportDate = ?, fld_inventoryID = ?, fld_userID = ?, fld_reportDescription = ?, fld_roomID = ?" +
                    " WHERE fld_errorReportID = ?");

            // due to MSSQL not having booleans, we chose to use a bit instead. therefor it needs to be translated
            // with 0 = false, 1 = true.
            if (errorReport.isArchived()) {
                ps.setInt(1, 1);
            } else {
                ps.setInt(1, 0);
            }

            ps.setDate(2, Date.valueOf(errorReport.getReportDate()));
            ps.setInt(3, errorReport.getInventoryItems().getId());
            ps.setInt(4, errorReport.getEmail().getEmailID());
            ps.setString(5, errorReport.getErrorReportDescription());
            ps.setInt(6, errorReport.getRoom().getRoomID());
            ps.setInt(7, errorReport.getErrorReportID());
            ps.executeUpdate();
            // closing the prepared statement.
            ps.close();
            LoggerMessage.info(this, "in update; removed error report : " + errorReport);
        } catch (SQLException e) {
            LoggerMessage.error(this, "in update: an error occurred " + e.getMessage());
        }
    }

    @Override
    public ErrorReport read(int id) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tbl_errorReport WHERE fld_errorReportID = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            boolean archived = rs.getBoolean("fld_archived");
            LocalDate date = rs.getDate("fld_reportDate").toLocalDate();
            int inventoryID = rs.getInt("fld_inventoryID");
            String description = rs.getString("fld_reportDescription");
            int userID = rs.getInt("fld_userID");
            int roomID = rs.getInt("fld_roomID");

            // closing ps and rs
            ps.close();
            rs.close();

            // creating room obj
            Room room = new Room();
            room.setRoomID(roomID);

            // creating email obj
            Email email = new Email(userID);

            // creating inventory obj
            InventoryItems inventoryItems = new InventoryItems(inventoryID);

            return new ErrorReport(room, email, inventoryItems, description, archived, date);


        } catch (SQLException e) {
            LoggerMessage.error(this, "in read: an error occurred " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<ErrorReport> readAll() {

        List<ErrorReport> errorReportList = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tbl_errorReport");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                boolean archived = rs.getBoolean("fld_archived");
                LocalDate date = rs.getDate("fld_reportDate").toLocalDate();
                int inventoryID = rs.getInt("fld_inventoryID");
                String description = rs.getString("fld_reportDescription");
                int userID = rs.getInt("fld_userID");
                int roomID = rs.getInt("fld_roomID");

                // creating room obj
                Room room = new Room();
                room.setRoomID(roomID);

                // creating email obj
                Email email = new Email(userID);

                // creating inventory obj
                InventoryItems inventoryItems = new InventoryItems(inventoryID);

                errorReportList.add(new ErrorReport(room, email, inventoryItems, description, archived, date));
            }

            // closing ps and rs
            ps.close();
            rs.close();


        } catch (SQLException e) {
            LoggerMessage.error(this, "in readAll; an error occurred " + e.getMessage());
        }

        return errorReportList;

    }
}
