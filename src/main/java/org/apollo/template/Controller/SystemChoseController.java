package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;


/*
    This class contains logic for chosing witch part of the system the user wants
    to use.
 */
public class SystemChoseController {

    @FXML
    private Button button_admin, button_infoScreen;

    @FXML
    protected void onButton_adminClick(){
        MainController.getInstance().setView(ViewList.LOGIN, BorderPaneRegion.CENTER);
        LoggerMessage.warning(this, "Loading Admin Login");

    }

    @FXML
    protected void onButton_infoScreen(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
        LoggerMessage.info(this, "Loading INFO Screen");
    }



}
