package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

public class ErrorReportController {

    @FXML
    private Button button_add, button_remove, button_save, button_back;

    @FXML
    private ChoiceBox<InventoryItems> inventoryItemsChoiceBox;

    @FXML
    protected void onButton_back(){
        LoggerMessage.error(this, "button_back needs to be linked to the right view");
        MainController.getInstance().setView(ViewList.HOME, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_save(){

    }

    @FXML
    protected void onButton_add(){

    }

    @FXML
    protected void onButton_remove(){

    }

    // method for loading inventory items.
    private void loadInventoryItems(){
        // clearing inventory choice box to prevent replica data.
        inventoryItemsChoiceBox.getItems().clear();



    }
}
