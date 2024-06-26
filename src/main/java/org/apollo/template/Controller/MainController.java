package org.apollo.template.Controller;

/*

    This it the main controller of the View, all other views are precedent with in this view.

 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.apollo.template.Service.Alert.Alertable;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.AlertComp;
import org.apollo.template.View.UI.AlertCompMessage;
import org.apollo.template.View.ViewList;
import org.apollo.template.ViewLoader;

import javax.swing.text.View;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, Alertable<VBox> {
    private static MainController INSTANCE;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox VBAlert;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setView(ViewList.SYSTEMCHOSE, BorderPaneRegion.CENTER);
        //setView(ViewList.CHOOSETIME, BorderPaneRegion.CENTER);
        //setView(ViewList.MENU, BorderPaneRegion.LEFT);
        setView(ViewList.TOPBAR, BorderPaneRegion.TOP);
        LoggerMessage.info(this, "Main Controller Initialized");
    }

    /**
     * Method for loading a container from a fxml file into the main Borderpane
     * @param viewList FXMLView
     * @param borderPaneRegion Region to load the fxml view into.
     */
    public void setView(ViewList viewList, BorderPaneRegion borderPaneRegion) {

        switch (borderPaneRegion){
            case TOP    -> borderPane.setTop(ViewLoader.loadView(viewList));
            case LEFT   -> borderPane.setLeft(ViewLoader.loadView(viewList));
            case RIGHT  -> borderPane.setRight(ViewLoader.loadView(viewList));
            case BOTTOM -> borderPane.setBottom(ViewLoader.loadView(viewList));
            case CENTER -> borderPane.setCenter(ViewLoader.loadView(viewList));
        }

    }

    /**
     * Method for clearing an area of a borderpane.
     * @param borderPaneRegion Region to set contents to null.
     */
    public void removeView(BorderPaneRegion borderPaneRegion) {

        switch (borderPaneRegion){
            case TOP    -> borderPane.setTop(null);
            case LEFT   -> borderPane.setLeft(null);
            case RIGHT  -> borderPane.setRight(null);
            case BOTTOM -> borderPane.setBottom(null);
            case CENTER -> borderPane.setCenter(null);
        }

    }







    private MainController() {
        if (INSTANCE == null) {
            LoggerMessage.info(this, "Creating an instance of " + this);
        }
    }

    public static MainController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainController();
        }
        return INSTANCE;
    }
    @Override
    public void getAlertArea(){
    }
    @Override
    public void clearAlertArea() {
        VBAlert.getChildren().clear();
    }
    @Override
    public void addAlert(AlertComp alertComp) {
        VBAlert.getChildren().add(alertComp);
    }
    @Override
    public void removeAlert(AlertComp alertComp) {
        VBAlert.getChildren().remove(alertComp);
    }
}