package org.apollo.template.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class TopbarController implements Initializable {

    @FXML
    private Label label_DateLabel, label_TimeLab;
    @FXML
    private Button button_adminLogin, button_home;

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

        // Make a timeline that updates the time displayed
        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            // Gets local time
            LocalTime localTime = LocalTime.now();
            // A formatter for converting the time into hours and minutes
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            // Applies the formatter
            String formattedTime = localTime.format(timeFormatter);

            label_TimeLab.setText(formattedTime);

        }));
        // The timeline runs an indefinite amount if times or until the program stops
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();

    }


    @FXML
    private void onButtonAdminLoginAction(){
        //TODO: direct to login screen instead of this!
        MainController.getInstance().setView(ViewList.ADMINMENU, BorderPaneRegion.LEFT);
    }


    @FXML
    private void onButtonHomeAction(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
    }
}
