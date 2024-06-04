package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

public class AdminLoginController {

    @FXML
    private VBox vBox_login;
    @FXML
    private PasswordField passwordField_password;

    //TODO: Change enum in setView(ViewList. to right view)
    /**
     * This method simulates an admin login to separate the functions between users who can only make ad-hoc bookings and admin users who have additional functionalities
     * Regardless of whether something is entered in the password field or the field is empty,
     * this method takes the user to the admin section
     * NOTE: we have not yet had lessons about login
     */
    @FXML
    private void onButtonLoginAction(){
        MainController.getInstance().setView(ViewList.ERRORREPORT, BorderPaneRegion.CENTER);
    }



}
