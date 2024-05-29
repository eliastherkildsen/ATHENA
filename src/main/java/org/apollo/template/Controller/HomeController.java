package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.UI.ReservedRoomsVBox;
import org.apollo.template.View.ViewList;

import java.awt.print.Book;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController {

    @FXML
    private AnchorPane root;

    // region buttons

    public void onBtnError(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
        new Alert(MainController.getInstance(), 3, AlertType.ERROR, "This is a test of Error The test is to show of this Alert component \n ert component").start();
    }
    public void onBtnInfo(){
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
