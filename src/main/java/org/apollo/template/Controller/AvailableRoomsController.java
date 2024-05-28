package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.apollo.template.View.UI.AvailableComponent;

import java.net.URL;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {

    //TODO: remember temporarily button in HomeController

    @FXML
    private VBox Vbox_Listview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AvailableComponent availableComponent = new AvailableComponent("301", "3. sal", 25, "Projektor, Højtaler, Airtame");

        Vbox_Listview.getChildren().add(availableComponent);
    }
}
