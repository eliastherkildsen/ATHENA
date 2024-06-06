package org.apollo.template.Controller.InventoryItems;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.InventoryItemDAO;

public class CreateInventoryItemController {

    private InventoryItems inventoryItems;

    @FXML
    private TextField textField_itemName;
    @FXML
    private TextArea textArea_itemDescription;

    @FXML
    protected void onButton_create(){
        // check if item name and description has been set.
        if (textField_itemName.getText().isEmpty() || textField_itemName == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke givet emnet et navn.")
                    .start();
            return;
        }

        if (textArea_itemDescription.getText().isEmpty() || textArea_itemDescription == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke givet emnet en beskrivelse.")
                    .start();
            return;
        }

        // creates inventory item obj, and stores it in the database.
        inventoryItems = new InventoryItems(textField_itemName.getText(), textArea_itemDescription.getText());
        DAO<InventoryItems> dao = new InventoryItemDAO();
        dao.add(inventoryItems);

        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu oprette et nyt emnu.")
                .start();


    }

    @FXML
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.SYSTEMCHOSE, BorderPaneRegion.CENTER);
    }



}
