package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Logger.LoggerMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDAO extends DAOAbstract<InventoryItems> {

    @Override
    public void add(InventoryItems inventoryItems) {

        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("INSERT INTO tbl_inventory (fld_inventoryName, fld_inventoryDescription) VALUES (?, ?)");
            ps.setString(1, inventoryItems.getName());
            ps.setString(2, inventoryItems.getDescription());
            ps.executeUpdate();
            ps.close();

            LoggerMessage.info(this, "Inserted, " + inventoryItems.getName() + " Into tbl_inventoryItem");

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN ADD; an error occurred: " + e.getMessage() + e.getSQLState() + e.getStackTrace().toString());
        }


    }

    @Override
    public void delete(InventoryItems inventoryItems) {
        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("DELETE FROM tbl_inventory WHERE fld_inventoryName = ?");
            ps.setString(1, inventoryItems.getName());
            ps.executeUpdate();

            LoggerMessage.info(this, "DELETED, " + inventoryItems.getName() + " FROM tbl_inventoryItem");


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN DELETE; an error occurred: " + e.getMessage());
            throw  new RuntimeException();
        } finally {
            closeprePareStatement(ps);
        }
    }

    @Override
    public void update(InventoryItems inventoryItems) {
        PreparedStatement ps = null;

        try {
            ps = getCONN().prepareStatement("UPDATE tbl_inventory SET fld_inventoryName = ?, fld_inventoryDescription = ? WHERE fld_inventoryID = ?");
            ps.setString(1, inventoryItems.getName());
            ps.setString(2, inventoryItems.getDescription());
            ps.setInt(3, inventoryItems.getId());

            LoggerMessage.info(this, "UPDATE, " + inventoryItems.getName() + " FROM tbl_inventoryItem");


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN UPDATE; an error occurred: " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }
    }


    @Override
    public InventoryItems read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_inventory WHERE fld_inventoryID = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            int inventoryID = rs.getInt("fld_inventoryID");
            String inventoryName = rs.getString("fld_inventoryName");
            String inventoryDescription = rs.getString("fld_inventoryDescription");

            return new InventoryItems(inventoryID, inventoryName, inventoryDescription);


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN READ; an error occurred: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeprePareStatement(ps);
        }

        return null;

    }

    @Override
    public List<InventoryItems> readAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InventoryItems> inventoryItemsList = new ArrayList<>();

        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_inventory");
            rs = ps.executeQuery();

            while (rs.next()) {
                int inventoryID = rs.getInt("fld_inventoryID");
                String inventoryName = rs.getString("fld_inventoryName");
                String inventoryDescription = rs.getString("fld_inventoryDescription");

                inventoryItemsList.add(new InventoryItems(inventoryID, inventoryName, inventoryDescription));

            }

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN READALL; an error occurred: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeprePareStatement(ps);
        }

        return inventoryItemsList;

    }
}
