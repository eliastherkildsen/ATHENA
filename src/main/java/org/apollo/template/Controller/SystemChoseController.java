package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apollo.template.Service.Logger.LoggerMessage;


/*
    This class contains logic for chosing witch part of the system the user wants
    to use.
 */
public class SystemChoseController {

    @FXML
    private Button button_admin, button_infoScreen;


    @FXML
    protected void onButton_adminClick(){
        //TODO: Link the admin view and logic.
        LoggerMessage.info(this, "onButton_adminClick needs to be linked to a view.");

    }

    @FXML
    protected void onButton_infoScreen(){
        //TODO: Link the info screen view and logic
        LoggerMessage.info(this, "onButton_infoScreen needs to be linked to a view.");
    }



}
