package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Model.Booking;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.GenerateCSV;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeController {
    // TODO Needs to die
    @FXML
    private AnchorPane root;

    // region buttons

    public void onBtnError(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
        new Alert(MainController.getInstance(), 3, AlertType.ERROR, "This is a test of Error The test is to show of this Alert component \n ert component").start();
    }
    public void onBtnInfo(){

        List<Booking> bookingInformationList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Booking booking = new Booking();
            booking.setDate(LocalDate.of(2024, 10, 10));
            booking.setRoom((new Room()));
            booking.setStartTime(Time.valueOf("10:00"));
            booking.setEndTime(Time.valueOf("11:00"));
            booking.setNumberOfParticipants(10);
            booking.setUsername("Ole Olesen");

            bookingInformationList.add(booking);
        }


        GenerateCSV csv = new GenerateCSV(bookingInformationList);
        new Alert(MainController.getInstance(),3, AlertType.INFO, "This is a test The test is to show of this \n asdasdkasdj akjs kdjas \n alksdlksadl ka lkasdl kalsk dl\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component").start();
    }
    public void onBtnSuccess(){
        MainController.getInstance().setView(ViewList.CHOOSETIME, BorderPaneRegion.CENTER);
        new Alert(MainController.getInstance(),3, AlertType.SUCCESS, "This is a test The test is to show of this \n asdasdkasdj akjs kdjas \n alksdlksadl ka lkasdl kalsk dl\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component").start();
    }
    public void onBtnAvailable(){
        MainController.getInstance().setView(ViewList.AVAILABLEROOMS, BorderPaneRegion.CENTER);
    }


    // endregion


}
