package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChooseTimeController implements Initializable {

    @FXML
    private GridPane gridPane_ButtonGrid;
    @FXML
    private Label label_StartTime;

    private int cnt, noOfButtons = 32;

    private int hour = 8, minute = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        while (cnt < noOfButtons){

            Button button = new Button("Button " + (cnt + 1));
            button.setPrefSize(114, 42);
            button.setOnAction(event -> {
                label_StartTime.setText(button.getText());
            });

            gridPane_ButtonGrid.add(button, cnt%4, cnt/4);

            cnt++;

        }


    }
}
