package org.apollo.template.Service.Utility;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Model.Booking;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.ReservedRoomsVBox;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetBookingsByDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingsByDate {

    public static void generateBookingsByDate(AnchorPane root, Date dateToday, VBox mainVbox){
        //Preparing an arraylist with our reservations
        List<Booking> bookingList = new ArrayList<>();
        LoggerMessage.debug("generateBookingsByDate"," "+bookingList);


        //MinMax required on root to display all information correctly.
        root.setMinHeight(700);
        root.setMinWidth(800);
        root.getChildren().add(mainVbox);
        root.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        //Ensuring VBox is Center to AnchorPane
        root.setTopAnchor(mainVbox, 0.0);
        root.setRightAnchor(mainVbox, 0.0);
        root.setLeftAnchor(mainVbox, 0.0);
        root.setBottomAnchor(mainVbox, 0.0);

        LoggerMessage.debug("BookingsByDate", "Before ");

        bookingList = GetBookingsByDate.getBookingsByDate(dateToday);
        scrollPaneGenerator(bookingList);

        LoggerMessage.debug("BookingsByDate", "after ");


        //If we got something from our stored Procedure we'll display that to the user.
        if (!bookingList.isEmpty()) {
            LoggerMessage.info(BookingsByDate.class,"Displaying results of today's bookings.");

            laberGenerator(mainVbox,"Dagens Møder og Bookinger:",40,Pos.CENTER_LEFT);

            ScrollPane sPane = scrollPaneGenerator(bookingList);
            //Add Scrollpane to the scene
            mainVbox.getChildren().add(sPane);

        } else {
            //Otherwise let's inform them nothing was found.

            laberGenerator(mainVbox,"Ingen planlagte møder/bookinger i dag.", 40, Pos.CENTER);
        }
    }


    public static ScrollPane scrollPaneGenerator(List<Booking> bookingList){
        //Setting up ScrolLPane
        ScrollPane sPane = new ScrollPane();
        sPane.setFitToWidth(true);
        sPane.getStyleClass().add("edge-to-edge"); //Remove 'edge' around scrollPane
        sPane.getStyleClass().add("custom-scroll-pane");
        sPane.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        //Populate the ScrollPane with my Bookings.
        if(bookingList == null) {
            LoggerMessage.info(BookingsByDate.class,"No bookings found. List Empty");
        } else {
            ReservedRoomsVBox vboxRooms = new ReservedRoomsVBox(bookingList);
            sPane.setContent(vboxRooms);
        }
        return sPane;
    }

    /**
     * Lable Generator, makes a label in a certain style
     * @param container Pane
     * @param labelText String
     * @param fontSize int
     * @param alignment Pos
     */
    public static void laberGenerator(Pane container, String labelText, int fontSize, Pos alignment ){
        Label label = new Label();
        label.setText(labelText);
        label.setTextFill(Paint.valueOf("WHITE"));
        label.setFont(Font.font(fontSize));
        container.getChildren().add(label);
        label.setAlignment(alignment);
    }
}


