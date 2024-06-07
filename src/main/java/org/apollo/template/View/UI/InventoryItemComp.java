package org.apollo.template.View.UI;

import javafx.scene.control.Label;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Model.Room;


public class InventoryItemComp extends DefualtComponent{

    private InventoryItems inventoryItems;

    public InventoryItemComp(InventoryItems inventoryItems) {
        this.inventoryItems = inventoryItems;
        loadInventoryComp();
    }

    /**
     * Method for loading the inventory component.
     */
    private void loadInventoryComp() {
        // initializing the labels.
        Label inventoryItemName = new Label(inventoryItems.getName());
        // styling the labels
        styleLable(inventoryItemName);

        // adding lable to this.
        this.getChildren().add(inventoryItemName);
    }

    // region getter & setter


    public InventoryItems getInventoryItems() {
        return inventoryItems;
    }

    // endregion


}
