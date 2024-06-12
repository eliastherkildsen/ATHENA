package org.apollo.template.Controller.Menu;

import javafx.fxml.FXML;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.GenerateCSV;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

public class AdminMenuController {

    @FXML
    protected void onButton_GenerateBooking(){
        MainController.getInstance().setView(ViewList.ADMINCREATEBOOKING, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_errorReport(){
        MainController.getInstance().setView(ViewList.CREATEERRORREPORT, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_viewErrorReport(){
        MainController.getInstance().setView(ViewList.VIEWERRORREPORT, BorderPaneRegion.CENTER);
    }
    @FXML
    protected void onButton_Room(){
        MainController.getInstance().setView(ViewList.ALLROOMS, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_Booking() { MainController.getInstance().setView(ViewList.ADMINVIEWBOOKINGS, BorderPaneRegion.CENTER); }

    @FXML
    protected void onButton_Statistics() { MainController.getInstance().setView(ViewList.STATISTICS, BorderPaneRegion.CENTER); }

    @FXML
    protected void onButton_GenerateCSV() {
        GenerateCSV generateCSV = new GenerateCSV();
        new Alert(MainController.getInstance(), 5, AlertType.INFO, "CSV lavet \nDu kan nu finde din CSV i root af programmet. \n" + generateCSV.getCsvFilePath()).start();
        LoggerMessage.debug(this,"CSV Made : " + generateCSV.toString());
    }

    @FXML
    protected void onButton_item() { MainController.getInstance().setView(ViewList.INVENTORYITEMS, BorderPaneRegion.CENTER); }

    @FXML
    protected void onButton_team() { MainController.getInstance().setView(ViewList.TEAMVIEW, BorderPaneRegion.CENTER); }





}
