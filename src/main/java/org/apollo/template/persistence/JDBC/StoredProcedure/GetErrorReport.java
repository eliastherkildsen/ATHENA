package org.apollo.template.persistence.JDBC.StoredProcedure;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetErrorReport {

    public static List<ErrorReport> getErrorReports(){
        PreparedStatement ps = null;
        List<ErrorReport> errorReports = new ArrayList<ErrorReport>();

        try {
            ps = JDBC.get().getConnection().prepareStatement("EXECUTE getErrorReports");
            ResultSet rs = ps.executeQuery();


            while (rs.next()){

                // email
                String emailAdress = rs.getString("fld_userEmail");
                int userID = rs.getInt("fld_userID");
                Email email = new Email(userID, emailAdress);

                // room
                String roomName = rs.getString("fld_roomName");
                int roomID = rs.getInt("fld_roomID");
                Room room = new Room(roomID, roomName);

                // inventory
                String inventoryName = rs.getString("fld_inventoryName");
                int inventoryID = rs.getInt("fld_inventoryID");
                InventoryItems inventoryItems = new InventoryItems(inventoryID, inventoryName);

                // error report
                boolean isArchived = rs.getBoolean("fld_archived");
                int errorReportID = rs.getInt("fld_errorReportID");
                LocalDate errorDate = rs.getDate("fld_reportDate").toLocalDate();
                String errorDescription = rs.getString("fld_reportDescription");

                ErrorReport errorReport = new ErrorReport(errorReportID, room, email, inventoryItems, errorDescription, isArchived, errorDate);
                LoggerMessage.debug("GetErrorReport", "Created new error report" + errorReport);
                errorReports.add(errorReport);
            }
            LoggerMessage.debug("GetErrorReport", "Loaded error reports: with length of " + errorReports.size());

            return errorReports;

        } catch (SQLException e) {
            LoggerMessage.error("GetErrorReport", e.getMessage());
            throw new RuntimeException();
        }
    }

}
