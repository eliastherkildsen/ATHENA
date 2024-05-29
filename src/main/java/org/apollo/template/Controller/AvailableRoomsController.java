package org.apollo.template.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.AvailableRoom;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.AvailableComponent;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetAvailableRooms;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {

    //TODO: remember temporarily button in HomeController

    @FXML
    private VBox vbox_Listview;

    private AvailableComponent availableComponent;
    private BookingInformation bookingInformation = new BookingInformation();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // getting today's date
        //Date dateToday = Date.valueOf(LocalDate.now());
        Date dateToday = Date.valueOf("2024-05-28");

        // search for available rooms today's date and saves them as a List
        List<AvailableRoom> roomsAvailableToday = GetAvailableRooms.getAvailableRooms(dateToday);

        // indsæt
        insertComponents(roomsAvailableToday);

    }


    /**
     *
     * @param availableRooms
     */
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
}
