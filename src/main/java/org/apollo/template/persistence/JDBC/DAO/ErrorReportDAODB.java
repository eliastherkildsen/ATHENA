package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ErrorReportDAODB implements DAO<ErrorReportDAODB> {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(ErrorReportDAODB errorReportDAODB) {

        try {
            PreparedStatement ps =  conn.prepareStatement("INSERT INTO tbl_errorReport (fld_archived, " +
                    "fld_reportDate, fld_inventoryID, fld_userID) VALUES ?,?,?,?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(ErrorReportDAODB errorReportDAODB) {

    }

    @Override
    public void update(ErrorReportDAODB errorReportDAODB) {

    }

    @Override
    public ErrorReportDAODB read(int id) {
        return null;
    }

    @Override
    public List<ErrorReportDAODB> readAll() {
        return List.of();
    }
}
