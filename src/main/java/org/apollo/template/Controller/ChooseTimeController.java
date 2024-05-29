package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseTimeController implements Initializable {

    @FXML
    private GridPane gridPane_ButtonGrid;
    @FXML
    private Label label_StartTime, label_EndTime;

    private int cnt, noOfButtons = 32, institutionInterval = 15;

    private List<Button> buttonList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> times = generateTimes(noOfButtons);

        while (cnt < noOfButtons){

            Button button = new Button(times.get(cnt));
            buttonList.add(button);
            button.setPrefSize(114, 42);
            button.setOnAction(event -> {

                String selectedTime = button.getText();
                handleTimeSelection(selectedTime);

//                if (label_StartTime.getText().isEmpty()){
//                    label_StartTime.setText(button.getText());
//
//                } else if (label_StartTime.getText().hashCode() < button.getText().hashCode()){
//                    label_EndTime.setText(button.getText());
//
//                } else if (label_EndTime.getText().isEmpty() ){
//                    label_EndTime.setText(label_StartTime.getText());
//                    label_StartTime.setText(button.getText());
//
//                } else if (label_StartTime.getText().hashCode() > button.getText().hashCode()){
//                    label_StartTime.setText(button.getText());
//
//                } else if (label_EndTime.getText().hashCode() < button.getText().hashCode()){
//                    label_EndTime.setText(button.getText());
//                }


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

        LocalTime selectedLocalTime = LocalTime.parse(selectedTime);
        // All cases where both labels are not filled
        if (label_StartTime.getText().isEmpty()){
            label_StartTime.setText(selectedTime);

        } else if (label_EndTime.getText().isEmpty()){
            if (isEarlier(label_StartTime.getText(), selectedTime)){
                label_EndTime.setText(selectedTime);

            } else {
                label_EndTime.setText(label_StartTime.getText());
                label_StartTime.setText(selectedTime);
            }
            // All cases where the labels are filled
        } else {
            LocalTime startTime = LocalTime.parse(label_StartTime.getText());
            LocalTime endTime = LocalTime.parse(label_EndTime.getText());

            if (selectedLocalTime.isBefore(startTime)){
                label_StartTime.setText(selectedTime);

            } else if (selectedLocalTime.isAfter(endTime)){
                label_EndTime.setText(selectedTime);

            } else {
                // If the selected time is between the already chosen times
                label_StartTime.setText(selectedTime);
                // If the time in label_StartTime is before the newly selected time, then set label_EndTime as end time if false then set it to startTime
                label_EndTime.setText(startTime.isBefore(selectedLocalTime) ?
                        endTime.format(DateTimeFormatter.ofPattern("HH:mm")) : startTime.format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        }

    }

    /**
     * A Method for generating all the time slots that need to be given to the different buttons in the grid
     * @param numberOfTimes How many times the method shoe generate
     * @return Returns a list of all the generated times
     */
    private List<String> generateTimes(int numberOfTimes){

        List<String> times = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8,0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

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

    @FXML
    protected void onButton_book(){
        // fetching start and end time.
        String endTime = label_EndTime.getText();
        String startTime = label_StartTime.getText();

        System.out.println("------- Time chooser------");
        System.out.println(endTime);
        System.out.println(startTime);

        // creating bookingInformation obj.
        // TODO needs to check if time has been selected.
        BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setStartTime(startTime);
        bookingInformation.setEndTime(endTime);

        System.out.println(bookingInformation.getStartTime() + " : " + bookingInformation.getEndTime());


        // changing view
        MainController.getInstance().setView(ViewList.BOOKINGINFO, BorderPaneRegion.CENTER);

        // publishing changes
        MessagesBroker.getInstance().publish(MessagesBrokerTopic.BOOKING_INFORMATION, bookingInformation);


    }
}
