package org.apollo.template.Controller.InventoryItems;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.InventoryItemComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.InventoryItemDAO;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ItemsController implements Initializable {

    private InventoryItems selectedItem;
    private List<InventoryItemComp> items = new ArrayList<>();
    private final DAO<InventoryItems> DAO = new InventoryItemDAO();

    @FXML
    private VBox vbox_inventoryItems;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::loadInventoryItems);
    }

    private void loadInventoryItems() {

        // fetch all inventory items from the database.
        // create componet for all the items.
        for (InventoryItems item : DAO.readAll()) {

            InventoryItemComp inventoryItemComp = new InventoryItemComp(item);

            // adding on actions for the items.
            inventoryItemComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                   unselectAllComponents();
                   // select this comp
                   inventoryItemComp.setCompColor(CompColors.SELECTED);
                   selectedItem = item;

                }
            });
            // adding component to items list.
            items.add(inventoryItemComp);
        }


        // adding the component to the vbox.
        vbox_inventoryItems.getChildren().addAll(items);


    }

    private void unselectAllComponents() {
        for (InventoryItemComp item : items) {
            item.setCompColor(CompColors.NORMAL);
        }
    }


    @FXML
    protected void onButton_delete(){
        // validate that an inventory item has been selected.
        if (!checkIfSelected()) return;

        // check if the item appears as foreign key
        try {
            DAO.delete(selectedItem);
        } catch (RuntimeException e) {
            // alert user that the item can not be deleted due to it being used in rooms or in an error report.
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du kan ikke slette dette item" +
                    "da det er en del af et rum eller en fejlmedelse!").start();
            return;
        }

        // alert the user that it went well,
        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu slette emnet!")
                .start();

    }

    @FXML
    protected void onButton_edit(){
        // validate that an inventory item has been selected.
        if (!checkIfSelected()) return;

        // transfer user to edit inventory item view.
        MainController.getInstance().setView(ViewList.EDITINVENTORYITEMS, BorderPaneRegion.CENTER);

        // publishing selected item
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.INVENTORY_ITEM, selectedItem);

    }

    @FXML
    protected void onButton_add(){
        // send user to view for creating an item
        MainController.getInstance().setView(ViewList.CREATEINVENTORYITEMS, BorderPaneRegion.CENTER);
    }

    /**
     * Method for validating that an item has been selected. Starts alert.
     * @return Boolean
     */
    private boolean checkIfSelected(){
        if(selectedItem == null){
            // alert user to select an item.
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en emne!")
                    .start();
            return false;
        }

        return true;

    }

}
