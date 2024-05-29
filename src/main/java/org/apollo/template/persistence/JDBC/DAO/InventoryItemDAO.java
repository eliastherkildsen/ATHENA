package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.io.ObjectStreamClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDAO implements DAO<InventoryItems> {

    private Connection conn = JDBC.get().getConnection();

    @Override
    public void add(InventoryItems inventoryItems) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO tbl_inventory (fld_inventoryName, fld_inventoryDescription) VALUES ?, ?");
            ps.setString(1, inventoryItems.getName());
            ps.setString(2, inventoryItems.getDescription());
            ps.executeQuery();
            ps.close();

            LoggerMessage.info(this, "Inserted, " + inventoryItems.getName() + " Into tbl_inventoryItem");

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN ADD; an error occurred: " + e.getMessage());
        }


    }

    @Override
    public void addAll(List<InventoryItems> list) {

        // TODO: this is highly in effective, and needs to be rewritten. Right now the ps is opened and closed everytime!
        for (InventoryItems inventoryItems : list){
            add(inventoryItems);
        }

    }

    @Override
    public void delete(InventoryItems inventoryItems) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM tbl_inventory WHERE fld_name = ?");
            ps.setString(1, inventoryItems.getName());
            ps.executeQuery();
            ps.close();

            LoggerMessage.info(this, "DELETED, " + inventoryItems.getName() + " FROM tbl_inventoryItem");


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN DELETE; an error occurred: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(List<InventoryItems> list) {

        // TODO: this is highly in effective, and needs to be rewritten. Right now the ps is opened and closed everytime!
        for (InventoryItems inventoryItems : list){
            delete(inventoryItems);
        }
    }

    @Override
    public void update(InventoryItems inventoryItems) {

    }

    @Override
    public void updateAll(List<InventoryItems> t) {

    }

    @Override
    public InventoryItems read(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_inventory WHERE fld_inventoryID = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            int inventoryID = rs.getInt("fld_inventoryID");
            String inventoryName = rs.getString("fld_inventoryName");
            String inventoryDescription = rs.getString("fld_inventoryDescription");

            ps.close();
            rs.close();

            return new InventoryItems(inventoryID, inventoryName, inventoryDescription);


        } catch (SQLException e) {
            LoggerMessage.error(this, "IN READ; an error occurred: " + e.getMessage());
        }

        return null;

    }

    @Override
    public List<InventoryItems> readAll() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InventoryItems> inventoryItemsList = new ArrayList<>();

        try {
            ps = conn.prepareStatement("SELECT * FROM tbl_inventory");
            rs = ps.executeQuery();

            while (rs.next()) {
                int inventoryID = rs.getInt("fld_inventoryID");
                String inventoryName = rs.getString("fld_inventoryName");
                String inventoryDescription = rs.getString("fld_inventoryDescription");

                inventoryItemsList.add(new InventoryItems(inventoryID, inventoryName, inventoryDescription));

            }
            ps.close();
            rs.close();

        } catch (SQLException e) {
            LoggerMessage.error(this, "IN READALL; an error occurred: " + e.getMessage());
        }

        return inventoryItemsList;

    }
}
