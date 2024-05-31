package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.*;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.ReservedRoomsVBox;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class InfoScreenController implements Initializable {

    Boolean meetingsFound;

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Preparing an arraylist with our reservations
        //TODO: List<BookingInformationSimple> booking = new ArrayList<>();
        List<Booking> bookingList = new ArrayList<>();

        //Setting up the view with a Vbox
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");
        mainVbox.setAlignment(Pos.CENTER);

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

        //Getting Todays date.
        Date dateToday = Date.valueOf(LocalDate.now());
        LoggerMessage.debug(this, "Todays date: " + dateToday);

        /**
         * Running our stored SQL PROCEDURE
         * NOTE: We are sorting the data we get by time in the stored PROCEDURE
         * Returns:
         *    SELECT
         *         tbl_booking.fld_startTime,
         *         tbl_booking.fld_endTime,
         *         tbl_booking.fld_userName,
         *         tbl_room.fld_roomName,
         * 		   tbl_meetingType.fld_meetingType
         */
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC GetBookingsByDate @BookingDate = ?");
            ps.setDate(1, dateToday);
            ResultSet rs = ps.executeQuery();
            meetingsFound = rs.next();
            //No result?
            if (!meetingsFound){
                LoggerMessage.info(this, "No results found for " + dateToday);
            } else {
                //Else lets do stuff with the result - Do while to ensure all results are included.
                //Do-while to ensure we include the first result from our resultset.
                do {
                    Booking booking = new Booking();
                    Room room = new Room();
                    room.setRoomName("fld_roomName");
                    Time startTime = rs.getTime("fld_startTime");
                    Time endTime = rs.getTime("fld_endTime");
                    String username = rs.getString("fld_username");
                    MeetingType meetingType = new MeetingType(rs.getString("fld_meetingType"));


                    booking.setStartTime(startTime);
                    booking.setEndTime(endTime);
                    booking.setUsername(username);
                    booking.setRoom(room);
                    booking.setMeetingType(meetingType);

                    bookingList.add(booking);
                    LoggerMessage.debug(this, "Size of ArrayList : " + bookingList.size());
                    LoggerMessage.info(this,"Arraylist Created.");
                } while (rs.next());
            }

        } catch (SQLException e) {
            LoggerMessage.warning(this,"Have you installed, ");
            LoggerMessage.error(this,"Stored Procedure : GetBookingsByDate didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }

        //If we got something from our stored Procedure we'll display that to the user.
        if (meetingsFound) {
            LoggerMessage.info(this,"Displaying results of today's bookings.");

            laberGenerator(mainVbox,"Dagens Møder og Bookinger:",40,Pos.CENTER_LEFT);
            //Setting up ScrolLPane
            ScrollPane sPane = new ScrollPane();
            sPane.setFitToWidth(true);
            sPane.getStyleClass().add("edge-to-edge"); //Remove 'edge' around scrollPane
            sPane.getStyleClass().add("custom-scroll-pane");
            sPane.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

            //Populate the ScrollPane with my Bookings.
            ReservedRoomsVBox vboxRooms = new ReservedRoomsVBox(bookingList);
            sPane.setContent(vboxRooms);

            //Add Scrollpane to the scene
            mainVbox.getChildren().add(sPane);

        } else {
            //Otherwise let's inform them nothing was found.

            laberGenerator(mainVbox,"Ingen planlagte møder/bookinger i dag.", 40, Pos.CENTER);
        }

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
            MainController.getInstance().setView(ViewList.DELETEBOOKING, BorderPaneRegion.CENTER);
        });

        pane.getChildren().add(deleteBookingButton);
    }


    /**
     *
     * @param container
     * @param labelText
     * @param fontSize
     * @param alignment
     */
    public void laberGenerator(Pane container, String labelText, int fontSize, Pos alignment ){
        Label label = new Label();
        label.setText(labelText);
        label.setTextFill(Paint.valueOf("WHITE"));
        label.setFont(Font.font(fontSize));
        container.getChildren().add(label);
        label.setAlignment(alignment);
    }


    // endregion


}
