package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Model.BookingTime;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.*;

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
    private GridPane gridPane_ButtonGrid;
    @FXML
    private Button button_Start, button_End;

    private int cnt, noOfButtons = 32, institutionInterval = 15;

    private boolean startBool = false, endBool = false;

    private List<Button> buttonList = new ArrayList<>();

    private BookingInformation bookingInformation;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    // JDBC Connection
    private Connection conn = JDBC.get().getConnection();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.BOOKING_INFORMATION);
        // On action for time selected buttons
        button_Start.setOnAction(event -> {
            startBool = true;
            button_Start.setStyle("-fx-font-weight: bold; -fx-font-size: 18");
        });
        button_End.setOnAction(event -> {
            endBool = true;
            button_End.setStyle("-fx-font-weight: bold; -fx-font-size: 18");
        });

        List<String> times = generateTimes(noOfButtons);

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
            // TODO change back to object
            ps.setInt(2, 1 );
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
            LoggerMessage.error(this, "Error in checkIfStartTime");
        }

        return false;
    }



    @FXML
    protected void onButton_book(){
        // fetching start and end time.
        String endTime = button_End.getText();
        String startTime = button_Start.getText();

        System.out.println("------- Time chooser------");
        System.out.println(endTime);
        System.out.println(startTime);

        // creating bookingInformation obj.
        // TODO needs to check if time has been selected.
        bookingInformation.setStartTime(startTime);
        bookingInformation.setEndTime(endTime);

        System.out.println(bookingInformation.getStartTime() + " : " + bookingInformation.getEndTime());


        // changing view
        MainController.getInstance().setView(ViewList.BOOKINGINFO, BorderPaneRegion.CENTER);

        // publishing changes
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, bookingInformation);


    }

    @Override
    public void update(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }
}
