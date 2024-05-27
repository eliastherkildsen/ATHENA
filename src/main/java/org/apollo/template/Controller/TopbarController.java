package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TopbarController implements Initializable {

    @FXML
    private Label label_DateLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Gets the date of today
        LocalDate daysDate = LocalDate.now();
        // Format pattern that converts the month from "05" to "May"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        // Applies the formatter to the date
        String formattedDate = daysDate.format(formatter);
        // Sets the text field to display the formatted date
        label_DateLabel.setText(formattedDate);
    }
}
