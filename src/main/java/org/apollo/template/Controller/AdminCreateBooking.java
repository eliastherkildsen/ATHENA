package org.apollo.template.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminCreateBooking implements Initializable {

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //BoilerPlate Vbox, HBox and Labels
        VBox mainVbox = new VBox();
        mainVbox.setSpacing(20);
        mainVbox.setStyle("-fx-background-color: rgba(0, 159, 227, 0.8);");
        mainVbox.setAlignment(Pos.CENTER);

        Label title = new Label("Create Booking");
        title.getStyleClass().add("title");
        title.setTextFill(Paint.valueOf("WHITE"));
        title.setFont(Font.font(40));


        HBox dateAndMaxPeople = new HBox(title);
        dateAndMaxPeople.setAlignment(Pos.CENTER);

        //DatePickerStart Setup
        Label labelFromDate = new Label("Fra:");
        DatePicker datePickerStart = new DatePicker();
        datePickerStart.showWeekNumbersProperty();

        //DatePickerEnd Setup
        Label labelToDate = new Label("Til:");
        DatePicker datePickerEnd = new DatePicker();
        //DatePickerEnd, we are setting its value to today and we are disabling it for interaction
        datePickerEnd.setValue(LocalDate.now());
        datePickerEnd.setDisable(true);

        //We want to ensure that our datePickerStart is only allowing dates from today and forward.
        datepickerSetCell(datePickerStart,LocalDate.now());

        //A Listener to interact with our datePickerEnd
        datePickerStart.valueProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null){
                    //We are no longer disabling the datePickerEnd as a start date has been picked.
                    datePickerEnd.setDisable(false);
                    LoggerMessage.debug(this, "BEFORE START DATE: "+datePickerStart.getValue());
                    LoggerMessage.debug(this, "BEFORE END DATE: "+datePickerEnd.getValue());

                    //We are only changing the date of the datePickerEnd if a date is chosen that surpasses it
                    if ((datePickerEnd.getValue() == null) || newValue.isAfter(datePickerEnd.getValue())){
                        datePickerEnd.setValue(newValue);
                        LoggerMessage.debug(this, "NOW END DATE: "+datePickerEnd.getValue());
                    }
                    datepickerSetCell(datePickerEnd,newValue);

                } else {
                    datePickerEnd.setDisable(true);
                }
            }
        });

        //Setting out ComboBox
        Label labelMaxPeople = new Label("Antal Deltager:");
        ComboBox numberOfPeople = new ComboBox(getMaxPersonCountFromRooms());


        dateAndMaxPeople.getChildren().addAll(labelFromDate,datePickerStart,labelToDate, datePickerEnd,labelMaxPeople, numberOfPeople);
        mainVbox.getChildren().addAll(title, dateAndMaxPeople);

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

    }

    private static void datepickerSetCell(DatePicker datePickerStart, LocalDate newDate) {
        datePickerStart.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(newDate) < 0 );
            }
        });
    }

    private ObservableList<Integer> getMaxPersonCountFromRooms() {
        List<Room> roomsList = new ArrayList<>();
        RoomDAO roomDAO = new RoomDAO();
        roomsList = roomDAO.readAll();

        int maxPersonCount = Integer.MIN_VALUE; // Initialize to the smallest possible integer value
        Room maxRoom = null; // To keep track of the room with the maximum person count

        for (Room room : roomsList) {
            if (room.getRoomMaxPersonCount() > maxPersonCount) {
                maxPersonCount = room.getRoomMaxPersonCount();
                maxRoom = room;
                LoggerMessage.debug(this,"MaxRoomCount: " +  maxPersonCount);
                LoggerMessage.debug(this,"Room with Max Cap: " +  maxRoom.getRoomName());
            }
        }
        ObservableList<Integer> optionsList = FXCollections.observableArrayList();
        for (int i = 1; i <= maxPersonCount; i++) {
            optionsList.add(i);
        }
        return optionsList;
    }
}
