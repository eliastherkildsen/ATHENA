package org.apollo.template.Controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.TextFieldInputValidation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.MeetingTypeDAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetAllRoomTypeNames;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetRoomIDFromName;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetRoomTypeIDFromName;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.ResourceBundle;

public class EditRoomController implements Initializable, Subscriber {

    @FXML
    private ChoiceBox choiceBox_RoomType;
    @FXML
    private TextField textField_RoomName, textField_MaxCapacity, textField_Floor;

    Room room = new Room();
    MeetingType meetingType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Method for making sure that only Integers can be entered in the text fields
        TextFieldInputValidation.attatchIntegerValidation(textField_MaxCapacity, textField_Floor);

        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.ROOM_INFORMATION);
        loadRoomTypesCB();

    }

    @FXML
    protected void onButton_Confirm(){

        // Check if all the text fields are filled
        if(textField_RoomName.getText().isEmpty() || textField_MaxCapacity.getText().isEmpty() || textField_Floor.getText().isEmpty()){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Please fill all fields, before trying to create.")
                    .start();
            return;
        }

        String roomTypeName = (String) choiceBox_RoomType.getSelectionModel().getSelectedItem();
        RoomType roomType = GetRoomTypeIDFromName.getRoomTypeIDFromName(roomTypeName);
        // Gets information from the textfields
        Room room = new Room();
        room.setRoomName(textField_RoomName.getText());
        room.setRoomTypeID(roomType.getRoomTypeID());
        room.setFloor(Integer.parseInt(textField_Floor.getText()));
        room.setRoomMaxPersonCount(Integer.parseInt(textField_MaxCapacity.getText()));
        room.setRoomID(GetRoomIDFromName.getRoomIDFromName(room));
        // Updates the room with the new information
        DAO<Room> dao = new RoomDAO();
        dao.update(room);

        MainController.getInstance().setView(ViewList.AVAILABLEROOMS, BorderPaneRegion.CENTER);

        new Alert(MainController.getInstance(), 5, AlertType.INFO, "The room has been updated.")
                .start();
    }

    @FXML
    protected void onButton_Cancel(){
        MainController.getInstance().setView(ViewList.ALLROOMS, BorderPaneRegion.CENTER);
    }

    /**
     * Method for populating the room type choice box
     */
    private void loadRoomTypesCB(){

        choiceBox_RoomType.getItems().clear();

        choiceBox_RoomType.getItems().addAll(GetAllRoomTypeNames.getAllRoomTypeNames());

    }


    @Override
    public void update(Object o) {

        if (o instanceof Room){
            this.room = (Room)o;
            // Gets information form the given object and inserts it into the textfields
            DAO<Room> roomDAO = new RoomDAO();
            room = roomDAO.read(room.getRoomID());
            textField_RoomName.setText(room.getRoomName());
            textField_Floor.setText(String.valueOf(room.getFloor()));
            textField_MaxCapacity.setText(String.valueOf(room.getRoomMaxPersonCount()));

            DAO<MeetingType> meetingTypeDAO = new MeetingTypeDAO();
            meetingType = meetingTypeDAO.read(room.getRoomTypeID());
            choiceBox_RoomType.getSelectionModel().select(meetingType.getMeetingTypeName());
        }
    }
}
