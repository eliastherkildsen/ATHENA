package org.apollo.template.Controller;

import javafx.fxml.FXML;
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
    protected void onButton_item() { MainController.getInstance().setView(ViewList.INVENTORYITEMS, BorderPaneRegion.CENTER); }





}
