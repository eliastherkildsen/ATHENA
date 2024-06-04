package org.apollo.template.Service.Utility;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.ReservedRoomsVBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingsByDate {

    static Boolean meetingsFound;

    public static void generateBookingsByDate(AnchorPane root, Date dateToday, VBox mainVbox){
        //Preparing an arraylist with our reservations
        List<Booking> bookingList = new ArrayList<>();

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


        LoggerMessage.debug(BookingsByDate.class, "Todays date: " + dateToday);

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
                LoggerMessage.info(BookingsByDate.class, "No results found for " + dateToday);
            } else {
                //Else lets do stuff with the result - Do while to ensure all results are included.
                //Do-while to ensure we include the first result from our resultset.
                do {
                    Booking booking = new Booking();
                    Room room = new Room();
                    room.setRoomName(rs.getString("fld_roomName"));
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
                    LoggerMessage.debug(BookingsByDate.class, "Size of ArrayList : " + bookingList.size());
                    LoggerMessage.info(BookingsByDate.class,"Arraylist Created.");
                } while (rs.next());
            }

        } catch (SQLException e) {
            LoggerMessage.warning(BookingsByDate.class,"Have you installed, ");
            LoggerMessage.error(BookingsByDate.class,"Stored Procedure : GetBookingsByDate didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }

        //If we got something from our stored Procedure we'll display that to the user.
        if (meetingsFound) {
            LoggerMessage.info(BookingsByDate.class,"Displaying results of today's bookings.");

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
    }


    /**
     *
     * @param container
     * @param labelText
     * @param fontSize
     * @param alignment
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


