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
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Alert.Alertable;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminCreateBooking implements Initializable {
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

        //SEARCH BUTTON
        Button buttonSearch = new Button("SEARCH");
        buttonSearch.setOnAction(event -> onButton_buttonSearch());

        //Adding elements to our FirstHBox
        hBoxDateAndMaxPeopleManipulation.getChildren().addAll(labelFromDate, datePickerStart, labelToDate, datePickerEnd, labelIncludeWeekends, checkBoxIncludeWeekends, labelMaxPeople, numberOfPeople);

        //Adding elements to our SecondHBox
        hBoxTimeManipulationAndConfirmation.getChildren().addAll(labelFrom, vBoxFromTimeHour, vBoxFromTimeMin, paneSpacer, labelTo, vBoxToTimeHour, vBoxToTimeMinutes, buttonSearch);

        //Adding elements to our VBox
        mainVbox.getChildren().addAll(title, hBoxDateAndMaxPeopleManipulation, hBoxTimeManipulationAndConfirmation);

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
        // Validation, if we are getting NULLs or Are doing something irrelevant lets return without doing anything else.
        if (datePickerStart.getValue() == null || datePickerEnd.getValue() == null || numberOfPeople.getValue() == null || comboBoxFromTimeHour.getValue() == null || comboBoxFromTimeMinutes.getValue() == null || comboBoxToTimeHour.getValue() == null || comboBoxToTimeMinutes.getValue() == null) {
            new Alert(MainController.getInstance() ,5, AlertType.ERROR, "Validation Error \nPlease fill in all fields before searching.").start();
            LoggerMessage.warning(this,"onButton_buttonSearch : Values returned Null, return from method");
            return;
        } else if (comboBoxFromTimeHour.getValue() + comboBoxFromTimeMinutes.getValue() == comboBoxToTimeHour.getValue() + comboBoxToTimeMinutes.getValue()) {
            new Alert(MainController.getInstance() ,5, AlertType.ERROR, "Booking Error \nYou cannot book a room for 0 minutes.").start();
            LoggerMessage.warning(this,"onButton_buttonSearch : Values TIME to and FROM are equal");
            return;
        }

        // Collecting the required information
        LocalDate startDate = datePickerStart.getValue();
        LocalDate endDate = datePickerEnd.getValue();
        boolean includeWeekends = checkBoxIncludeWeekends.isSelected();
        int maxPeople = numberOfPeople.getValue();
        int startHour = comboBoxFromTimeHour.getValue();
        int startMinute = comboBoxFromTimeMinutes.getValue();
        int endHour = comboBoxToTimeHour.getValue();
        int endMinute = comboBoxToTimeMinutes.getValue();

        // Processing the collected information
        LoggerMessage.debug("AdminCreateBooking", "START DATE: " + startDate);
        LoggerMessage.debug("AdminCreateBooking", "END DATE: " + endDate);
        LoggerMessage.debug("AdminCreateBooking", "INCLUDE WEEKENDS: " + includeWeekends);
        LoggerMessage.debug("AdminCreateBooking", "MAX PEOPLE: " + maxPeople);
        LoggerMessage.debug("AdminCreateBooking", "START TIME: " + startHour + ":" + startMinute);
        LoggerMessage.debug("AdminCreateBooking", "END TIME: " + endHour + ":" + endMinute);

        // Logic
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

    private ComboBox<Integer> comboBoxHour(int startHour, int endHour) {
        ComboBox<Integer> comboBoxHours = new ComboBox<>();
        for (int i = startHour; i < endHour; i++) {
            comboBoxHours.getItems().add(i);
        }
        comboBoxHours.setValue(startHour);
        return comboBoxHours;
    }

    private static HBox GenerateHBox (ArrayList<Node> controls){
        HBox hBox = new HBox();
        for (Node control : controls) {
            hBox.getChildren().add(control);
        }
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        return hBox;
    }

    private static void datepickerSetCell(DatePicker datePickerStart, LocalDate newDate) {
        datePickerStart.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(newDate));
            }
        });
    }

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

    private List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesList = new ArrayList<>();
        LocalDate date = startDate;

        while (!date.isAfter(endDate)){
            datesList.add(date);
            date = date.plusDays(1);
        }
        return datesList;
    }

    private List<LocalDate> removeDateWeekends(List<LocalDate> dates) {
        dates.removeIf(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
        return dates;
    }
}
