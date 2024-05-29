package org.apollo.template.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.AvailableRoom;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.AvailableComponent;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable, Subscriber {

    //TODO: remember temporarily button in HomeController

    @FXML
    private VBox vbox_listDD, vbox_Listview;
    @FXML
    //private ScrollPane scrollPaneView;

    private AvailableComponent availableComponent;
    private BookingInformation bookingInformation = new BookingInformation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // getting today's date
        //Date dateToday = Date.valueOf(LocalDate.now());
        Date dateToday = Date.valueOf("2024-05-28");

        // search for available rooms today's date and saves them as a List
        List<AvailableRoom> roomsAvailableToday = storedSqlProcedure(dateToday);

        vbox_Listview.setPadding(new Insets(10));

        // TODO: comment
        insertComponents(roomsAvailableToday);



/*
        button_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                MainController.getInstance().setView(ViewList.CHOOSETIME, BorderPaneRegion.CENTER);
            }
        });

 */

        // TODO: custom component
        //Vbox_Listview.getChildren().add(availableComponent);
    }

    private void insertComponents(List<AvailableRoom> availableRooms) {

        for (AvailableRoom availableRoom : availableRooms) {
            AvailableComponent availableComponent = new AvailableComponent(availableRoom);

            vbox_Listview.getChildren().add(availableComponent);

            Button button_book = availableComponent.getButton();
            button_book.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    // create bookingInformation obj.
                    bookingInformation.setRoomId(availableRoom.getRoomID());

                    // sending the user to booking complite view.
                    MainController.getInstance().setView(ViewList.CHOOSETIME, BorderPaneRegion.CENTER);

                    // publish bookingInformation obj.
                    MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, bookingInformation);
                }
            });


        }
    }



    // TODO: move to another class?

    /**
     * This method executes our stored SQL procedure "getAvailableRooms",
     * which finds all rooms with available booking times for today's date.
     * NOTE: We sort the data based on total booking time in our stored procedure.
     * - Rooms are considered available if they are not booked or if the total booking
     * time for today's date is < 480 minutes (8 hours).
     *
     * @param dateToday The today's date for which available rooms are being searched.
     * @return A List of AvailableRoom objects containing available rooms for today's date
     */
            private List<AvailableRoom> storedSqlProcedure(Date dateToday){

                List<AvailableRoom> availableRooms = new ArrayList<>();

                try {
                    PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getAvailableRooms @BookingDate = ?");
                    ps.setDate(1, dateToday);
                    ResultSet rs = ps.executeQuery();

                    while (rs.next()) {

                        AvailableRoom availableRoom = new AvailableRoom(rs.getInt("fld_roomID"),
                                rs.getString("fld_roomName"),
                                rs.getString("fld_floor"),
                                rs.getString("fld_roomTypeName"),
                                rs.getInt("fld_roomMaxPersonCount"));

                        availableRooms.add(availableRoom);
                    }

                    return availableRooms;

                } catch (SQLException e) {
                    LoggerMessage.error(this, "Stored Procedure : getAvailableRooms didn't run as intended " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }


            @Override
            public void update (BookingInformation bookingInformation){

                this.bookingInformation = bookingInformation;

            }
}
