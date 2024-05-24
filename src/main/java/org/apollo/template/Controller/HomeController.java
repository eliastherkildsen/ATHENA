package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Debugger.DebugMessage;


public class HomeController {

    @FXML
    private AnchorPane root;

    // region buttons

    public void onBtnError(){
        new Alert(MainController.getInstance(), 3, AlertType.ERROR, "This is a test of Error The test is to show of this Alert component \n ert component").start();
    }
    public void onBtnInfo(){
        new Alert(MainController.getInstance(),3, AlertType.INFO, "This is a test The test is to show of this \n asdasdkasdj akjs kdjas \n alksdlksadl ka lkasdl kalsk dl\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component").start();
    }
    public void onBtnSuccess(){
        new Alert(MainController.getInstance(),3, AlertType.SUCCESS, "This is a test The test is to show of this \n asdasdkasdj akjs kdjas \n alksdlksadl ka lkasdl kalsk dl\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component\nAlert component").start();
    }


    // endregion


}
