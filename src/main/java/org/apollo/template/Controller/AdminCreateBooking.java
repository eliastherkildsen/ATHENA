package org.apollo.template.Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.MeetingType;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Alert.Alertable;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.AvailableComponent;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class AdminCreateBooking implements Initializable {
    Boolean bookingFound;
    private int openHour = 8;
    private int closingHour = 16;
    private int minuteInterval = 15;

    @FXML
    private AnchorPane root;
    @FXML
    private DatePicker datePickerStart;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private CheckBox checkBoxIncludeWeekends;

    @FXML
    private ComboBox<Integer> numberOfPeople;

    @FXML
    private ComboBox<Integer> comboBoxFromTimeHour;

    @FXML
    private ComboBox<Integer> comboBoxFromTimeMinutes;

    @FXML
    private ComboBox<Integer> comboBoxToTimeHour;

    @FXML
    private ComboBox<Integer> comboBoxToTimeMinutes;

    @FXML
    private VBox vBoxResult;

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

        HBox hBoxDateAndMaxPeopleManipulation = new HBox(title);
        hBoxDateAndMaxPeopleManipulation.setAlignment(Pos.CENTER);
        hBoxDateAndMaxPeopleManipulation.setSpacing(10);

        HBox hBoxTimeManipulationAndConfirmation = new HBox();
        hBoxTimeManipulationAndConfirmation.setAlignment(Pos.CENTER);
        hBoxTimeManipulationAndConfirmation.setSpacing(10);

        //DatePickerStart Setup
        Label labelFromDate = new Label("Fra:");
        datePickerStart = new DatePicker();
        datePickerStart.showWeekNumbersProperty();

        //DatePickerEnd Setup
        Label labelToDate = new Label("Til:");
        datePickerEnd = new DatePicker();

        //DatePickerEnd, we are setting its value to today, and we are disabling it for interaction
        datePickerEnd.setValue(LocalDate.now());
        datePickerEnd.setDisable(true);

        //We want to ensure that our datePickerStart is only allowing dates from today and forward.
        datepickerSetCell(datePickerStart, LocalDate.now());

        //A Listener to interact with our datePickerEnd
        datePickerStart.valueProperty().addListener(new ChangeListener<LocalDate>() {

            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null) {
                    //We are no longer disabling the datePickerEnd as a start date has been picked.
                    datePickerEnd.setDisable(false);
                    LoggerMessage.debug("AdminCreateBooking", "BEFORE START DATE: " + datePickerStart.getValue());
                    LoggerMessage.debug("AdminCreateBooking", "BEFORE END DATE: " + datePickerEnd.getValue());

                    //We are only changing the date of the datePickerEnd if a date is chosen that surpasses it
                    if ((datePickerEnd.getValue() == null) || newValue.isAfter(datePickerEnd.getValue())) {
                        datePickerEnd.setValue(newValue);
                        LoggerMessage.debug("AdminCreateBooking", "NOW END DATE: " + datePickerEnd.getValue());
                    }
                    datepickerSetCell(datePickerEnd, newValue);

                } else {
                    datePickerEnd.setDisable(true);
                }
            }
        });

        //Setting our CheckBox
        Label labelIncludeWeekends = new Label("Inkluder Weekender:");
        checkBoxIncludeWeekends = new CheckBox();

        //Setting our ComboBox
        Label labelMaxPeople = new Label("Antal Deltager:");
        ObservableList<Integer> maxPersonCounts = getMaxPersonCountFromRooms();
        numberOfPeople = new ComboBox<>(maxPersonCounts);

        //TimePicker ComboBoxes
        //FROM
        Label labelFrom = new Label("FRA: ");
        Label labelFromTimeHour = new Label("Timer");
        comboBoxFromTimeHour = comboBoxHour(openHour, closingHour);
        VBox vBoxFromTimeHour = new VBox(labelFromTimeHour, comboBoxFromTimeHour);

        Label labelFromTimeMinute = new Label("Minuter");
        comboBoxFromTimeMinutes = comboBoxTimeMinutesIntervalsInt(minuteInterval);
        VBox vBoxFromTimeMin = new VBox(labelFromTimeMinute, comboBoxFromTimeMinutes);

        //TO
        Label labelTo = new Label("TIL: ");
        Label labelToTimeHour = new Label("Timer");
        comboBoxToTimeHour = comboBoxHour(openHour, closingHour);
        VBox vBoxToTimeHour = new VBox(labelToTimeHour, comboBoxToTimeHour);

        Label labelToTimeMinute = new Label("Minuter");
        comboBoxToTimeMinutes = comboBoxTimeMinutesIntervalsInt(minuteInterval);
        VBox vBoxToTimeMinutes = new VBox(labelToTimeMinute, comboBoxToTimeMinutes);

        Pane paneSpacer = new Pane();
        paneSpacer.setMinWidth(10);

        //ScrollPane
        ScrollPane scrollPaneResult = new ScrollPane();
        scrollPaneResult.setFitToWidth(true);
        scrollPaneResult.getStyleClass().add("edge-to-edge"); //Remove 'edge' around scrollPane
        scrollPaneResult.getStyleClass().add("custom-scroll-pane");
        vBoxResult = new VBox();
        vBoxResult.setSpacing(5);
        scrollPaneResult.setContent(vBoxResult);

        //SEARCH BUTTON
        Button buttonSearch = new Button("SØG");
        buttonSearch.setOnAction(event -> onButton_buttonSearch());

        //Adding elements to our FirstHBox
        hBoxDateAndMaxPeopleManipulation.getChildren().addAll(labelFromDate, datePickerStart, labelToDate, datePickerEnd, labelIncludeWeekends, checkBoxIncludeWeekends, labelMaxPeople, numberOfPeople);

        //Adding elements to our SecondHBox
        hBoxTimeManipulationAndConfirmation.getChildren().addAll(labelFrom, vBoxFromTimeHour, vBoxFromTimeMin, paneSpacer, labelTo, vBoxToTimeHour, vBoxToTimeMinutes, buttonSearch);

        //Adding elements to our VBox
        mainVbox.getChildren().addAll(title, hBoxDateAndMaxPeopleManipulation, hBoxTimeManipulationAndConfirmation, scrollPaneResult);

        //MinMax required on root to display all information correctly.
        root.setMinHeight(700);
        root.setMinWidth(800);
        root.getChildren().add(mainVbox);
        root.setStyle("-fx-background-color: rgba(0, 159, 227, 0);");

        //Ensuring VBox is Center to AnchorPane
        AnchorPane.setTopAnchor(mainVbox, 0.0);
        AnchorPane.setRightAnchor(mainVbox, 0.0);
        AnchorPane.setLeftAnchor(mainVbox, 0.0);
        AnchorPane.setBottomAnchor(mainVbox, 0.0);
    }

    @FXML
    private void onButton_buttonSearch() {
        if (datePickerStart.getValue() == null || datePickerEnd.getValue() == null || numberOfPeople.getValue() == null || comboBoxFromTimeHour.getValue() == null || comboBoxFromTimeMinutes.getValue() == null || comboBoxToTimeHour.getValue() == null || comboBoxToTimeMinutes.getValue() == null) {
            new Alert(MainController.getInstance(), 5, AlertType.ERROR, "Validerings Fejl \nUdfyld venligst alle felter før søgning.").start();
            LoggerMessage.warning(this, "onButton_buttonSearch : Values returned Null, return from method");
            return;
        } else if (comboBoxFromTimeHour.getValue() == comboBoxToTimeHour.getValue() && comboBoxFromTimeMinutes.getValue() == comboBoxToTimeMinutes.getValue()) {
            new Alert(MainController.getInstance(), 5, AlertType.ERROR, "Booking Fejl \nDu kan ikke booke et rum i 0 minutter.").start();
            LoggerMessage.warning(this, "onButton_buttonSearch : Values TIME to and FROM are equal");
            return;
        } else if (comboBoxFromTimeHour.getValue() > comboBoxToTimeHour.getValue() || (comboBoxFromTimeHour.getValue() == comboBoxToTimeHour.getValue() && comboBoxFromTimeMinutes.getValue() > comboBoxToTimeMinutes.getValue())) {
            new Alert(MainController.getInstance(), 5, AlertType.ERROR, "Booking fejl \nTiden FRA kan ikke være efter TIL").start();
            LoggerMessage.warning(this, "onButton_buttonSearch : Start time cannot be after end time");
            return;
        }

        // Ensure that our results screen is empty before filling with new information.
        vBoxResult.getChildren().clear();

        // A place to store our rooms.
        List<Room> finalRoomList = new ArrayList<>();

        // We need these variables for later.
        LocalDate startDate = datePickerStart.getValue();
        LocalDate endDate = datePickerEnd.getValue();
        boolean excludeWeekends = checkBoxIncludeWeekends.isSelected();
        int maxPeople = numberOfPeople.getValue();
        int startHour = comboBoxFromTimeHour.getValue();
        int startMinute = comboBoxFromTimeMinutes.getValue();
        int endHour = comboBoxToTimeHour.getValue();
        int endMinute = comboBoxToTimeMinutes.getValue();

        // Converting our time to something the JDBC and SQL likes.
        LocalTime startTime = LocalTime.of(startHour, startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        java.sql.Time sqlStartTime = java.sql.Time.valueOf(startTime);
        java.sql.Time sqlEndTime = java.sql.Time.valueOf(endTime);

        List<LocalDate> datesList = getDatesBetween(startDate, endDate);
        if (excludeWeekends) {
            removeDateWeekends(datesList);
        }

        Map<LocalDate, List<Room>> dateToRoomListMap = new HashMap<>();

        // Trying our search for each date.
        try {
            for (LocalDate date : datesList) {
                PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC GetAvailableRoomsForDateTimeRange @startDate = ?, @startTime = ?, @endDate = ?, @endTime =?, @maxPersonCount =?");
                ps.setDate(1, Date.valueOf(date));
                ps.setTime(2, sqlStartTime);
                ps.setDate(3, Date.valueOf(date));
                ps.setTime(4, sqlEndTime);
                ps.setInt(5, maxPeople);
                ResultSet rs = ps.executeQuery();

                List<Room> roomList = new ArrayList<>();
                while (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getInt("fld_roomID"));
                    room.setRoomName(rs.getString("fld_roomName"));
                    room.setRoomMaxPersonCount(rs.getInt("fld_roomMaxPersonCount"));
                    room.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                    room.setFloor(rs.getInt("fld_floor"));

                    RoomType roomType = new RoomType();
                    roomType.setRoomTypeID(rs.getInt("fld_roomTypeID"));
                    roomType.setRoomTypeName(rs.getString("fld_roomTypeName"));
                    roomType.setRoomTypeDescription(rs.getString("fld_roomTypeDescription"));

                    room.setRoomType(roomType);

                    roomList.add(room);
                }

                LoggerMessage.debug(this, "Date: " + date + ", Room List Size: " + roomList.size());
                dateToRoomListMap.put(date, roomList);
            }
        } catch (SQLException e) {
            LoggerMessage.error(this, "Error in Stored PROCEDURE : " + e.getMessage());
            LoggerMessage.warning(this, "Have you installed PROCEDURE GetAvailableRoomsForDateTimeRange?");
            return;
        }

        // Find the common rooms across all dates
        if (!dateToRoomListMap.isEmpty()) {
            Collection<List<Room>> roomLists = dateToRoomListMap.values();
            finalRoomList = new ArrayList<>(roomLists.iterator().next());
            for (List<Room> roomList : roomLists) {
                finalRoomList.retainAll(roomList);
            }
        }

        LoggerMessage.debug(this, "Final Room List Size: " + finalRoomList.size());

        if (finalRoomList.isEmpty()) {
            new Alert(MainController.getInstance(), 10, AlertType.ERROR, "Booking fejl \nIngen ledig lokaler for den angivne periode.").start();
        }

        for (Room room : finalRoomList) {
            AvailableComponent test = new AvailableComponent(room);
            vBoxResult.getChildren().add(test);
        }
    }

    /**
     * Helper Class that takes the param int + i.
     * Is made to fit an hour / int 60.
     * @param Number
     * @return ComboBox
     */
    private ComboBox<Integer> comboBoxTimeMinutesIntervalsInt(int Number) {
        ComboBox<Integer> comboBoxMinutes = new ComboBox<>();
        for (int i = 0; i < 60; i += Number) {
            comboBoxMinutes.getItems().add(i);
        }
        comboBoxMinutes.setValue(0);
        return comboBoxMinutes;
    }

    /**
     * ComboBox generator that takes two Int values and displays the range between them within the ComboBox.
     * Intended to be used as a time picker.
     * @param startHour int
     * @param endHour int
     * @return Combobox Integer
     */
    private ComboBox<Integer> comboBoxHour(int startHour, int endHour) {
        ComboBox<Integer> comboBoxHours = new ComboBox<>();
        for (int i = startHour; i < endHour; i++) {
            comboBoxHours.getItems().add(i);
        }
        comboBoxHours.setValue(startHour);
        return comboBoxHours;
    }

    //TODO NOT USED DELETE OR USE
    private static HBox GenerateHBox (ArrayList<Node> controls){
        HBox hBox = new HBox();
        for (Node control : controls) {
            hBox.getChildren().add(control);
        }
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        return hBox;
    }

    /**
     * DataPickerSetCell formats a datapicker so that dates before newDate gets disabled.
     * @param datePickerStart datePicker
     * @param newDate LocalDate
     */
    private static void datepickerSetCell(DatePicker datePickerStart, LocalDate newDate) {
        datePickerStart.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(newDate));
            }
        });
    }

    /**
     * getMaxPersonCountFromRooms takes a list of rooms and returns a ObservableList with Intergers of the rooms MaxPersonCount
     * @return ObservableList Integer
     */
    private ObservableList<Integer> getMaxPersonCountFromRooms() {
        List<Room> roomsList = new ArrayList<>();
        RoomDAO roomDAO = new RoomDAO();
        roomsList = roomDAO.readAll();

        int maxPersonCount = Integer.MIN_VALUE; // Initialize to the smallest possible integer value

        for (Room room : roomsList) {
            if (room.getRoomMaxPersonCount() > maxPersonCount) {
                maxPersonCount = room.getRoomMaxPersonCount();
                LoggerMessage.debug(this,"MaxRoomCount: " +  maxPersonCount);
                LoggerMessage.debug(this,"Room with Max Cap: " +  room.getRoomName());
            }
        }
        ObservableList<Integer> optionsList = FXCollections.observableArrayList();
        for (int i = 1; i <= maxPersonCount; i++) {
            optionsList.add(i);
        }
        return optionsList;
    }

    /**
     * getDatesBetween takes two LocalDate objects and finds all dates in that range.
     * @param startDate LocalDate
     * @param endDate LocalDate
     * @return List LocalDate
     */
    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesList = new ArrayList<>();
        LocalDate date = startDate;

        while (!date.isAfter(endDate)){
            datesList.add(date);
            date = date.plusDays(1);
        }
        return datesList;
    }

    /**
     * removeDateWeekends removes dates from a List LocalDate if it contains Saturday or Sunday
     * @param dates List LocalDate
     * @return List LocalDate
     */
    private List<LocalDate> removeDateWeekends(List<LocalDate> dates) {
        dates.removeIf(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
        return dates;
    }
}
