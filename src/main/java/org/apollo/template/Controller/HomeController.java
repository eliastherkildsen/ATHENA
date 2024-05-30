package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.GenerateCSV;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HomeController {

    @FXML
    private AnchorPane root;

    // region buttons

    public void onBtnError(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
        new Alert(MainController.getInstance(), 3, AlertType.ERROR, "This is a test of Error The test is to show of this Alert component \n ert component").start();
    }
    public void onBtnInfo(){

        List<BookingInformation> bookingInformationList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            BookingInformation bookingInformation = new BookingInformation();
            bookingInformation.setDate(new Date(10-10-2024));
            bookingInformation.setRoom((new Room()));
            bookingInformation.setStartTime("10:00");
            bookingInformation.setEndTime("11:00");
            bookingInformation.setNumberOfParticipants(10);
            bookingInformation.setUserName("Ole Olesen");

            bookingInformationList.add(bookingInformation);
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
