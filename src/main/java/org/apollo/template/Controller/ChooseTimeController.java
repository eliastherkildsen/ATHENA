package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseTimeController implements Initializable {

    @FXML
    private GridPane gridPane_ButtonGrid;
    @FXML
    private Label label_StartTime, label_EndTime;

    private int cnt, noOfButtons = 32;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> times = generateTimes(noOfButtons);

        while (cnt < noOfButtons){

            Button button = new Button(times.get(cnt));
            button.setPrefSize(114, 42);
            button.setOnAction(event -> {

                if (label_StartTime.getText().isEmpty()){
                    label_StartTime.setText(button.getText());

                } else if (label_EndTime.getText().isEmpty() &&
                        label_StartTime.hashCode() > button.getText().hashCode()){
                    label_EndTime.setText(button.getText());

                } else {
                    label_EndTime.setText(label_StartTime.getText());
                    label_StartTime.setText(button.getText());
                }


            });

            gridPane_ButtonGrid.add(button, cnt%4, cnt/4);

            cnt++;

        }


    }

    /**
     * A Method for generating all the time slots that need to be given to the different buttons in the grid
     * @param numberOfTimes How many times the method shoe generate
     * @return Returns a list of all the generated times
     */
    private List<String> generateTimes(int numberOfTimes){

        List<String> times = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8,0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (int i = 0; i < numberOfTimes; i++) {
            times.add(startTime.plusMinutes(i * 15).format(formatter));
        }

        return times;
    }
}
