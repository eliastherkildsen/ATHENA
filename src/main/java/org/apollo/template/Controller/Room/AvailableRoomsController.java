package org.apollo.template.Controller.Room;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.AvailableComponent;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetAvailableRooms;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {


    @FXML
    private VBox vbox_Listview;

    private Booking booking = new Booking();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // getting today's date
        Date dateToday = Date.valueOf(LocalDate.now());

        // search for available rooms today's date and saves them as a List
        List<Room> roomsAvailableToday = GetAvailableRooms.getAvailableRooms(dateToday);

        // inserts available rooms into custom components and adds these components to the view
        insertComponents(roomsAvailableToday, dateToday);
    }

    /**
     * Method for inserting available rooms into custom components and adding them to the view.
     * Each component is also associated with a booking button and its corresponding action.
     * @param availableRooms the list of available rooms today's date
     */
    private void insertComponents(List<Room> availableRooms, Date dateToday) {

        if (availableRooms.isEmpty()){
            LoggerMessage.info(this, "No available rooms");

            allBooked(dateToday);
        }

        else {

            try {
                for (Room availableRoom : availableRooms) {

                    // creates a custom component object using the available room object
                    AvailableComponent availableComponent = new AvailableComponent(availableRoom);

                    // adds the custom component to the view
                    vbox_Listview.getChildren().add(availableComponent);

                    Button button_book = availableComponent.getButton();
                    button_bookOnAction(button_book, availableRoom);
                }

                LoggerMessage.info(this, "Available rooms added to view");

            } catch (Exception e) {
                LoggerMessage.error(this, "Failed to add available rooms to view\n" + e.getMessage());
            }
        }
    }

    /**
     * This method handles if no rooms available for booking today's date and prompts a message to user
     * @param dateToday the today's date
     */
    private void allBooked(Date dateToday) {

        try{
            Label label_noAvailableRooms = new Label(String.format("Ingen ledige lokaler %s", dateToday));
            label_noAvailableRooms.setFont(Font.font(40));

            vbox_Listview.getChildren().add(label_noAvailableRooms);
            vbox_Listview.setAlignment(Pos.CENTER);

            LoggerMessage.info(this, "Text: \"tNo available rooms\" added to view");

        }catch (Exception e){
            LoggerMessage.error(this, "Failed to add text: \"tNo available rooms\" to view\n" + e.getMessage());
        }
    }


    /**
     * This method sets the action for the booking button to handle room booking events.
     * @param button_book the button that will trigger the booking action
     * @param availableRoom the room object that is available for booking
     */
    private void button_bookOnAction(Button button_book, Room availableRoom) {

        button_book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                // create booking object
                booking.setRoom(availableRoom).setAdHoc(true);
                LoggerMessage.info(this, "Booking object created");


                // sending the user to choose time view
                MainController.getInstance().setView(ViewList.CHOOSETIME, BorderPaneRegion.CENTER);

                // publish booking information object
                try {
                    MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, booking);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
