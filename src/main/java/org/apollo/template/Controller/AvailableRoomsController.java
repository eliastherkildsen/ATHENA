package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.apollo.template.View.UI.AvailableComponent;

public class AvailableRoomsController {

    @FXML
    private VBox Vbox_Listview;

    AvailableComponent availableComponent = new AvailableComponent("301", "3. sal", 25, "Projektor, Højtaler, Airtame");
}
