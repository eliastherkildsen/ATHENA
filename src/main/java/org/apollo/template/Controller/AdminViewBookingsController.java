package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.BookingsByDate;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetBookingsByDate;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static org.apollo.template.Service.Utility.BookingsByDate.laberGenerator;

public class AdminViewBookingsController implements Initializable {

    @FXML
    private AnchorPane root;

    Date dateToday;
    VBox mainVbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateToday = Date.valueOf(LocalDate.now());

        //Setting up the view with a Vbox
        mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");
        mainVbox.setAlignment(Pos.CENTER);
        BookingsByDate.generateBookingsByDate(root, dateToday, mainVbox);

        setUpForwardsBackWardsHBox(mainVbox);
        //Adding our button at the bottom of the screen.
        bookRoomToday(mainVbox);
        // adding delete booking bottom
        deleteBooking(mainVbox);
        //mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 1);");
        LoggerMessage.info(this,"InfoView initialized");
    }

    private void oneDayForward(Pane pane){
        Button button_Forward = new Button();
        button_Forward.setPrefSize(80, 80);
        button_Forward.setText(">>");
        button_Forward.setAlignment(Pos.CENTER);

        button_Forward.setOnAction(event -> {
            // Code to execute when the button is clicked
            LocalDate date = dateToday.toLocalDate().plusDays(1);
            dateToday = Date.valueOf(date);
            mainVbox.getChildren().clear();
            laberGenerator(mainVbox,"Dagens Møder og Bookinger:",40,Pos.CENTER_LEFT);
            mainVbox.getChildren().add(BookingsByDate.scrollPaneGenerator(GetBookingsByDate.getBookingsByDate(dateToday)));
            setUpForwardsBackWardsHBox(mainVbox);
            bookRoomToday(mainVbox);
            deleteBooking(mainVbox);
        });
        pane.getChildren().add(button_Forward);
    }

    private void oneDayBackward(Pane pane){
        Button button_Backward = new Button();
        button_Backward.setPrefSize(80, 80);
        button_Backward.setText("<<");
        button_Backward.setAlignment(Pos.CENTER);

        button_Backward.setOnAction(event -> {
            // Code to execute when the button is clicked
            if (Objects.equals(dateToday.toLocalDate(), LocalDate.now())) {
                new Alert(MainController.getInstance(), 5, AlertType.INFO, "You can not go further back.")
                        .start();
                return;
            }
            LocalDate date = dateToday.toLocalDate().minusDays(1);
            dateToday = Date.valueOf(date);
            mainVbox.getChildren().clear();
            laberGenerator(mainVbox,"Dagens Møder og Bookinger:",40,Pos.CENTER_LEFT);
            mainVbox.getChildren().add(BookingsByDate.scrollPaneGenerator(GetBookingsByDate.getBookingsByDate(dateToday)));
            setUpForwardsBackWardsHBox(mainVbox);
            bookRoomToday(mainVbox);
            deleteBooking(mainVbox);
        });
        pane.getChildren().add(button_Backward);
    }

    /**
     * Helper method that adds a button.
     * @param pane
     */
    private void bookRoomToday(Pane pane){
        Button bookButton = new Button();
        bookButton.setPrefSize(200,100);
        bookButton.setText("BOOK");
        bookButton.setAlignment(Pos.CENTER);

        bookButton.setOnAction(event -> {
            // Code to execute when the button is clicked
            MainController.getInstance().setView(ViewList.AVAILABLEROOMS, BorderPaneRegion.CENTER);
        });

        pane.getChildren().add(bookButton);
    }

    private void deleteBooking(Pane pane){
        Button deleteBookingButton = new Button();
        deleteBookingButton.setPrefSize(200,50);
        deleteBookingButton.setText("SLET BOOKING");
        deleteBookingButton.setAlignment(Pos.CENTER);

        deleteBookingButton.setOnAction(event -> {
            // Code to execute when the button is clicked
            MainController.getInstance().setView(ViewList.ADMINDELETEBOOKING, BorderPaneRegion.CENTER);
        });

        pane.getChildren().add(deleteBookingButton);
    }

    /**
     * Method for setting up the HBox that contains the buttons for going forwards and backwards in bookings
     */
    private void setUpForwardsBackWardsHBox(Pane pane){

        HBox hBox_ForwardsBackwards = new HBox();
        hBox_ForwardsBackwards.setSpacing(50);
        hBox_ForwardsBackwards.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");
        hBox_ForwardsBackwards.setAlignment(Pos.CENTER);
        hBox_ForwardsBackwards.setPrefSize(200, 75);

        // Adding backward button
        oneDayBackward(hBox_ForwardsBackwards);
        // Add date label
        Label label_Date = new Label();
        label_Date.setText(dateToday.toLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM")));
        label_Date.setTextFill(Paint.valueOf("WHITE"));
        label_Date.setFont(Font.font(18));
        hBox_ForwardsBackwards.getChildren().add(label_Date);
        // Adding forward button
        oneDayForward(hBox_ForwardsBackwards);

        pane.getChildren().add(hBox_ForwardsBackwards);
    }

}
