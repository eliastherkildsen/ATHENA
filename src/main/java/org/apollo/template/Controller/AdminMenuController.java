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
        MainController.getInstance().setView(ViewList.ERRORREPORT, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_Room(){
        MainController.getInstance().setView(ViewList.ALLROOMS, BorderPaneRegion.CENTER);
    }





}
