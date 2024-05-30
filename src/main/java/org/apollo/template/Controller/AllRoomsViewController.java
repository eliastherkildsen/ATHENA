package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.apollo.template.Model.Room;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AllRoomsViewController implements Initializable {

    @FXML
    private ListView listView_RoomList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DAO<Room> roomDAO = new RoomDAO();
        List<Room> roomList = roomDAO.readAll();

        for (Room room : roomList) {
            listView_RoomList.getItems().add(room.toString());
        }

    }

    @FXML
    protected void onButton_CreateRoom(){
        MainController.getInstance().setView(ViewList.CREATEROOM, BorderPaneRegion.CENTER);
    }
}
