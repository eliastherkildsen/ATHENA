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
import javafx.scene.text.Font;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Model.ReservedRoomDate;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.ReservedRoomsVBox;
import org.apollo.template.View.ViewList;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<ReservedRoomDate> booking = new ArrayList<>();

        //Setting up the view with a Vbox
        VBox mainVbox = new VBox();
        mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");
        mainVbox.setAlignment(Pos.CENTER);

        Label header = new Label("No Meetings Today");
        header.setText("No Planned Meetings Today");
        header.setFont(Font.font(40));
        mainVbox.getChildren().add(header);
        header.setAlignment(Pos.CENTER);



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
                do {
                    ReservedRoomDate reservation = new ReservedRoomDate(
                            rs.getString("fld_startTime"),
                            rs.getString("fld_endTime"),
                            rs.getString("fld_userName"),
                            rs.getString("fld_roomName"),
                            rs.getString("fld_meetingType")
                    );
                    booking.add(reservation);
                    //LoggerMessage.debug(this, "ResultSet : " + rs.getString("fld_userName"));
                    LoggerMessage.debug(this, "Size of ArrayList : " + booking.size());
                } while (rs.next());
            }

        } catch (SQLException e) {
            LoggerMessage.error(this,"Stored Procedure : GetBookingsByDate didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }


        //If we got something from our stored Procedure we'll display that to the user.
        if (meetingsFound) {
            LoggerMessage.info(this,"Displaying results of today's bookings.");
            //Setting up ScrolLPane
            ScrollPane sPane = new ScrollPane();
            sPane.setFitToWidth(true);
            sPane.getStyleClass().add("edge-to-edge"); //Remove 'edge' around scrollPane
            sPane.getStyleClass().add("custom-scroll-pane");
            sPane.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

            ReservedRoomsVBox vboxRooms = new ReservedRoomsVBox(booking);
            sPane.setContent(vboxRooms);

            mainVbox.getChildren().add(sPane);
            System.out.println("ScrollPane style: " + sPane.getStyle());

        } else {
            //Otherwise let's inform them nothing was found.

            Label noresult = new Label("No Meetings Today");
            noresult.setText("No Planned Meetings Today");
            noresult.setFont(Font.font(40));
            mainVbox.getChildren().add(noresult);
            noresult.setAlignment(Pos.CENTER);

        }

        //Adding our button at the bottom of the screen.
        bookRoomToday(mainVbox);
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

    public void laberGenerator(){

    }


    // endregion


}
