package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.BookingsByDate;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetBookingsByDate;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminViewBookings implements Initializable {

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
        System.out.println("Hej 1");
        BookingsByDate.generateBookingsByDate(root, dateToday, mainVbox);



        //Adding our button at the bottom of the screen.
        bookRoomToday(mainVbox);
        // adding delete booking bottom
        deleteBooking(mainVbox);
        //mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 1);");
        LoggerMessage.info(this,"InfoView initialized");
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
            LocalDate date = dateToday.toLocalDate().plusDays(1);
            dateToday = Date.valueOf(date);
            mainVbox.getChildren().clear();
            BookingsByDate.scrollPaneGenerator(GetBookingsByDate.getBookingsByDate(dateToday));
            deleteBooking(mainVbox);
            bookRoomToday(mainVbox);
        });

        pane.getChildren().add(deleteBookingButton);
    }

}
