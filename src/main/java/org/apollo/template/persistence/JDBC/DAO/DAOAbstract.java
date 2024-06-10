package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Service.Logger.LoggerMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAOAbstract<T> implements DAO<T>{
    // reference to connection obj
    private final Connection CONN = JDBC.get().getConnection();

    /**
     * Method for fetching the connection obj
     * @return Connection
     */
    public Connection getCONN() {
        return CONN;
    }

    /**
     * Method for closing a PreparedStatement, Checks for obj null
     * catches SQLException
     * @param ps PreparedStatement
     */
    public void closeprePareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                LoggerMessage.error(this, "Error occurred during closing of PreparedStatement : " + e.getMessage());
            }
        }
    }

    /**
     * Method for closing a ResultSet, Checks for obj null
     * catches SQLException
     * @param rs ResultSet
     */
    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LoggerMessage.error(this,"Error occurred during closing of ResultSet : " + e.getMessage());
            }
        }
    }


}
