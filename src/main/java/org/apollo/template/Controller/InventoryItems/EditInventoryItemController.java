package org.apollo.template.Controller.InventoryItems;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.InventoryItemComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.InventoryItemDAO;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class EditInventoryItemController implements Subscriber, Initializable {

    private InventoryItems inventoryItems;

    @FXML
    private TextField textField_itemName;
    @FXML
    private TextArea textArea_itemDescription;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribe to messages broker. of inventory obj
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.INVENTORY_ITEM);


        Platform.runLater(this::loadInventoryObject);
    }

    /**
     * Method for loading data from the inventory object into text area en text field.
     */
    private void loadInventoryObject() {
        // validate that inventoryItem is not null
        if (inventoryItems != null) {
            textArea_itemDescription.setText(inventoryItems.getDescription());
            textField_itemName.setText(inventoryItems.getName());

        }
    }

    @FXML
    protected void onButton_update(){
        // check if item description has been set.
        if (textArea_itemDescription.getText().isEmpty()){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke givet emnet en beskrivelse.")
                    .start();
            return;
        }

        // edits inventory item obj, and stores it in the database.
        inventoryItems.setDescription(textArea_itemDescription.getText());
        DAO<InventoryItems> dao = new InventoryItemDAO();
        dao.update(inventoryItems);

        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu oprette et nyt emnu.")
                .start();


    }

    @FXML
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.SYSTEMCHOSE, BorderPaneRegion.CENTER);
    }


    @Override
    public void update(Object o) {
        // validating that the object recived is of type inventoryItems
        if (o instanceof InventoryItems) {
            inventoryItems = (InventoryItems) o;
        }
        else {
            LoggerMessage.warning(this, "in update; Expected inventory object, but recived " + o);
        }



    }
}
