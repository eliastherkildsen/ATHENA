package org.apollo.template.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetNumberOfBookingsFromRoomID;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllRoomsViewController implements Initializable {

    @FXML
    private ListView<Room> listView_RoomList;

    private int selectedRoomID = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DAO<Room> roomDAO = new RoomDAO();
        List<Room> roomList = roomDAO.readAll();

        for (Room room : roomList) {
            listView_RoomList.getItems().add(room);
        }
        // Sets a on Mouse click event, so that when an element is selected the variable is updated
        listView_RoomList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                Room room = listView_RoomList.getSelectionModel().getSelectedItem();
                selectedRoomID = room.getRoomID();
            }
        });

    }

    @FXML
    protected void onButton_CreateRoom(){
        MainController.getInstance().setView(ViewList.CREATEROOM, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_Edit(){
        if (selectedRoomID == -1){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en error report.")
                    .start();
            return;
        }
        Room room = new Room();
        room.setRoomID(selectedRoomID);

        MainController.getInstance().setView(ViewList.EDITROOM, BorderPaneRegion.CENTER);
        // Updates the room object
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.ROOM_INFORMATION, room);

    }

    @FXML
    protected void onButton_Delete(){
        // Check for if something is chosen
        if(selectedRoomID == -1){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt et lokale!").start();
            return;
        }

        Room room = new Room();
        room.setRoomID(selectedRoomID);

        int noOfBookings = GetNumberOfBookingsFromRoomID.getNumberOfBookingsFromRoomID(room);
        // Check for if there is any bookings in the room that is getting deleted
        if (noOfBookings > 0) {
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Der er bookinger tilknyttet til dette lokale!").start();
            return;
        }
        // Creating dao
        DAO<Room> dao = new RoomDAO();
        dao.delete(room);
        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu slettet lokalet").start();

    }

}
