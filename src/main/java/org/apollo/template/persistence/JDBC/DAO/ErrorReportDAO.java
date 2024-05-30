package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ErrorReportDAO implements DAO<ErrorReportDAO> {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(ErrorReportDAO errorReportDAO) {

        try {
            PreparedStatement ps =  conn.prepareStatement("INSERT INTO tbl_errorReport (fld_archived, " +
                    "fld_reportDate, fld_inventoryID, fld_userID) VALUES ?,?,?,?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(ErrorReportDAO errorReportDAO) {

    }

    @Override
    public void update(ErrorReportDAO errorReportDAO) {

    }

    @Override
    public ErrorReportDAO read(int id) {
        return null;
    }

    @Override
    public List<ErrorReportDAO> readAll() {
        return List.of();
    }
}
