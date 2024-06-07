package org.apollo.template.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Room;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.RoomComp;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private VBox vbox_room;
    private Room selectedRoom = null;
    private List<RoomComp> roomComps = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoomVbox();
    }

    private void loadRoomVbox() {

        // clearing vbox to avoid replica data.
        vbox_room.getChildren().clear();

        // load all rooms from database.
        DAO<Room> roomDAO = new RoomDAO();
        List<Room> roomList = roomDAO.readAll();

        // create room component for all rooms.
        for (Room room : roomList) {

            // creating the room component.
            RoomComp roomComp = new RoomComp(room);

            // adding roomComp to list for easy iteration when unselecting.
            roomComps.add(roomComp);

            // attatching on action
            roomComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // unselect all components, to make sure that only one component is seleceted at a time.
                    unselectAllRoomComponents();
                    // changing roomComp color
                    roomComp.setCompColor(CompColors.SELECTED);
                    // settinge the selected room
                    selectedRoom = room;
                }
            });

        }

        // adding roomcomponents to vbox_room
        vbox_room.getChildren().addAll(roomComps);

    }

    /**
     * Method for setting all room components colors to NORMAL.
     */
    private void unselectAllRoomComponents() {
        for (RoomComp roomComp : roomComps) {
            roomComp.setCompColor(CompColors.NORMAL);
        }
    }
}
