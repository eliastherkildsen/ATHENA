package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Model.BookingTime;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseTimeController implements Initializable, Subscriber {

    @FXML
    private Button button_Start, button_End;
    @FXML
    private Label label_RoomName;
    @FXML
    private VBox vbox_FirstVBox;

    private int cnt, noOfButtons = 32, institutionInterval = 15;

    private boolean startBool = false, endBool = false;

    private List<Button> buttonList = new ArrayList<>();

    private Booking booking;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    // JDBC Connection
    private Connection conn = JDBC.get().getConnection();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);

        System.out.println("initialized");

    }

    /**
     * Method for generating all the buttons for the grid pane
     */
    private void generateScrollableGridWithButtons(){
        List<String> times = generateTimes(noOfButtons);

        GridPane gridPane_ButtonGrid = new GridPane();

        int numRows = (int) Math.ceil(noOfButtons/4.0);

        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setMinHeight(70);  // Minimum height
            rowConstraints.setVgrow(Priority.ALWAYS); // Allow the row to grow
            gridPane_ButtonGrid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0; i < 4; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setHalignment(HPos.CENTER);
            columnConstraints.setHgrow(Priority.ALWAYS);
            gridPane_ButtonGrid.getColumnConstraints().add(columnConstraints);
        }

        while (cnt < noOfButtons){

            Button button = new Button(times.get(cnt));

            // Method for checking if a given button is a start time
            button.setDisable(checkIfStartTime(button.getText()));

            buttonList.add(button);
            button.setPrefSize(114, 42);
            button.setOnAction(event -> {

                String selectedTime = button.getText();
                handleTimeSelection(selectedTime);

            });

            gridPane_ButtonGrid.add(button, cnt%4, cnt/4);

            cnt++;

        }

        ScrollPane scrollPane = new ScrollPane(gridPane_ButtonGrid);
        scrollPane.getStyleClass().add("custom-scroll-pane");
        scrollPane.setFitToWidth(true);
        vbox_FirstVBox.getChildren().add(scrollPane);
    }

    /**
     * Method for setting up the on action
     */
    private void setOnActionForStartAndEnd(){
        // On action for time selected buttons
        button_Start.setOnAction(event -> {
            startBool = true;
            button_Start.setStyle("-fx-font-weight: bold; -fx-font-size: 18");
        });
        button_End.setOnAction(event -> {
            endBool = true;
            button_End.setStyle("-fx-font-weight: bold; -fx-font-size: 18");
        });
    }

    /**
     * Method for handling all the logic for the buttons being able to select and switch the chosen times
     * @param selectedTime The time selected by the user
     */
    private void handleTimeSelection(String selectedTime){

        if (startBool) {
            button_Start.setText(selectedTime);
            button_Start.setStyle("-fx-font-weight: normal; -fx-font-size: 18");
        }

        if (endBool) {
            button_End.setText(selectedTime);
            button_End.setStyle("-fx-font-weight: normal; -fx-font-size: 18");
        }

        LocalTime selectedLocalTime = LocalTime.parse(selectedTime);
        // All cases where both labels are not filled
        if (button_Start.getText().isEmpty() && (!startBool || !endBool)){
            button_Start.setText(selectedTime);

        } else if (button_End.getText().isEmpty()){
            if (isEarlier(button_Start.getText(), selectedTime)){
                button_End.setText(selectedTime);

            } else {
                button_End.setText(button_Start.getText());
                button_Start.setText(selectedTime);
            }
        }

        startBool = false;
        endBool = false;
    }

    /**
     * A Method for generating all the time slots that need to be given to the different buttons in the grid
     * @param numberOfTimes How many times the method shoe generate
     * @return Returns a list of all the generated times
     */
    private List<String> generateTimes(int numberOfTimes){

        List<String> times = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8,0);


        for (int i = 0; i < numberOfTimes; i++) {
            times.add(startTime.plusMinutes(i * institutionInterval).format(formatter));
        }

        return times;
    }

    /**
     * A method for comparing times in HH:mm format.
     * @param time1 First time
     * @param time2 Second time
     * @return True if time1 is earlier than time2
     */
    private boolean isEarlier(String time1, String time2){
        LocalTime t1 = LocalTime.parse(time1);
        LocalTime t2 = LocalTime.parse(time2);
        return t1.isBefore(t2);
    }

    /**
     * Method for checking if a given time is the starting time of a meeting.
     * @param time Start Time of a meeting
     * @return Returns true if the given time is between any timeslots gotten from the database
     */
    private boolean checkIfStartTime(String time){

        LocalTime selectedTime = LocalTime.parse(time, formatter);
        try {

            Date todaysDate = Date.valueOf(LocalDate.now());

            PreparedStatement ps = conn.prepareStatement("EXECUTE getBookingsFromDate @BookingDate = ?, @RoomID = ?");

            ps.setDate(1, todaysDate);
            ps.setInt(2, booking.getRoom().getRoomID());
            List<BookingTime> bookingTimes = new ArrayList<>();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String startTime = rs.getString("fld_startTime");
                String endTime = rs.getString("fld_endTime");

                BookingTime bookingTime = new BookingTime(startTime, endTime);
                bookingTimes.add(bookingTime);
            }

            for (BookingTime bookingTime : bookingTimes) {

                LocalTime startTime = LocalTime.parse(bookingTime.getStartTime());
                LocalTime endTime = LocalTime.parse(bookingTime.getEndTime());

                if (!selectedTime.isBefore(startTime) && !selectedTime.isAfter(endTime.minusMinutes(institutionInterval))) {
                    return true;
                }

            }

        } catch (SQLException e){
            LoggerMessage.error(this, "Error in checkIfStartTime: \n" + e.getMessage());
        }

        return false;
    }

    /**
     * Method that checks if there is any disabled times in the period selected.
     * @param startTime Starting time of the period selected.
     * @param endTime Ending time of the period selected.
     * @return Returns true if the period selected contains a disabled time.
     */
    private boolean hasDisabledTimes(String startTime, String endTime){
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

        for (Button button : buttonList) {
            LocalTime buttonTime = LocalTime.parse(button.getText(), formatter);
            if (!buttonTime.isBefore(start) && buttonTime.isAfter(end) && button.isDisabled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method for setting the room name label to the correct room
     */
    private void setRoomNameLabel(){

        try{
            PreparedStatement ps = conn.prepareStatement("EXECUTE getRoomNameFromID @RoomID = ?");

            ps.setInt(1, booking.getRoom().getRoomID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                label_RoomName.setText("Lokale " + rs.getString("fld_roomName"));
            }

        }catch (SQLException e){
            LoggerMessage.error(this, "Error in setRoomNameLabel: \n" + e.getMessage());
        }

    }



    @FXML
    protected void onButton_book(){
        // fetching start and end time.
        String endTime = button_End.getText();
        String startTime = button_Start.getText();

        // Checks if there is any disabled times in the period selected.
        if (hasDisabledTimes(startTime, endTime)){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "The selected period includes times that are already booked.")
                    .start();
            return;
        }

        // adding seconds to the time obj to avoid casting errors.
        startTime += ":00";
        endTime += ":00";

        // creating bookingInformation obj.
        // TODO needs to check if time has been selected.
        booking.setStartTime(Time.valueOf(startTime));
        booking.setEndTime(Time.valueOf(endTime));

        LocalDate currentDate = LocalDate.now();
        booking.setDate(Date.valueOf(currentDate).toLocalDate());

        System.out.println(booking.getStartTime() + " : " + booking.getEndTime());


        // changing view
        MainController.getInstance().setView(ViewList.BOOKINGINFO, BorderPaneRegion.CENTER);

        // publishing changes
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, booking);


    }

    @Override
    public void update(Object o) {

        // validates that the object is of instance bookingInformation
        if (o instanceof Booking) {
            this.booking = (Booking) o;

            setRoomNameLabel();
            setOnActionForStartAndEnd();
            generateScrollableGridWithButtons();
        }
    }
}
