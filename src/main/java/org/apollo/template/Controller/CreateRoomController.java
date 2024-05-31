package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetAllRoomTypeNames;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetRoomTypeIDFromName;


import java.net.URL;
import java.util.ResourceBundle;

public class CreateRoomController implements Initializable {

    @FXML
    private ChoiceBox choiceBox_RoomType;
    @FXML
    private TextField textField_RoomName, textField_MaxCapacity, textField_Floor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoomTypesCB();

        System.out.println("initialized");
    }

    /**
     * Method for populating the room type choice box
     */
    private void loadRoomTypesCB(){

        choiceBox_RoomType.getItems().clear();

        choiceBox_RoomType.getItems().addAll(GetAllRoomTypeNames.getAllRoomTypeNames());

    }

    @FXML
    protected void onButton_Create(){

        if(textField_RoomName.getText().isEmpty() || textField_MaxCapacity.getText().isEmpty() || textField_Floor.getText().isEmpty()){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Please fill all fields, before trying to create.")
                    .start();
            return;
        }

        String roomTypeName = (String) choiceBox_RoomType.getSelectionModel().getSelectedItem();
        RoomType roomType = GetRoomTypeIDFromName.getRoomTypeIDFromName(roomTypeName);

        Room room = new Room();
        room.setRoomName(textField_RoomName.getText());
        room.setRoomTypeID(roomType.getRoomTypeID());
        room.setFloor(Integer.parseInt(textField_Floor.getText()));
        room.setRoomMaxPersonCount(Integer.parseInt(textField_MaxCapacity.getText()));

        DAO<Room> dao = new RoomDAO();
        dao.add(room);
        // TODO: Change to the correct view when it is made
        MainController.getInstance().setView(ViewList.AVAILABLEROOMS, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_Cancel(){
        MainController.getInstance().setView(ViewList.ALLROOMS, BorderPaneRegion.CENTER);
    }
}