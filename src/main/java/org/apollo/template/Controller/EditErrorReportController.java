package org.apollo.template.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.ErrorReportDAODB;
import org.apollo.template.persistence.JDBC.DAO.InventoryItemDAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;
import org.apollo.template.persistence.PubSub.Subscriber;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditErrorReportController implements Initializable, Subscriber {

    private ErrorReport errorReport;

    @FXML
    private ChoiceBox<Room> roomChoiceBox;
    @FXML
    private ChoiceBox<InventoryItems> inventoryItemsChoiceBox;

    @FXML
    private TextArea textArea_description; 
    
    @FXML 
    private TextField textField_email;

    @FXML
    private Button button_save, button_back;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // subscribing to messages broker
        MessagesBroker.getInstance().subscribe(this, MessagesBrokerTopic.ERROR_REPORT);

        // load email textfield
        Platform.runLater(this::loadEmailTextField);

        // load item choose box.
        Platform.runLater(this::loadItemChooseBox);

        // load room choose box.
        Platform.runLater(this::loadRoomChooseBox);

        // load text area error report description
        Platform.runLater(this::loadTextArea);

    }

    private void loadItemChooseBox() {
        // fetching all inventory items from the database.
        DAO< InventoryItems> inventoryItemsDAO = new InventoryItemDAO();
        List<InventoryItems> inventoryItemsList= inventoryItemsDAO.readAll();

        // loading items in to the choose box.
        inventoryItemsChoiceBox.getItems().addAll(inventoryItemsList);
        inventoryItemsChoiceBox.getSelectionModel().select(errorReport.getInventoryItems());

    }


    private void loadRoomChooseBox() {
        // fetching all inventory items from the database.
        DAO<Room> roomDAO = new RoomDAO();
        List<Room> roomList= roomDAO.readAll();

        // loading items in to the choose box.
        roomChoiceBox.getItems().addAll(roomList);
        roomChoiceBox.getSelectionModel().select(errorReport.getRoom());

    }

    private void loadEmailTextField() {

        textField_email.setText(errorReport.getEmail().getEmail());

    }

    private void loadTextArea(){
        textArea_description.setText(errorReport.getErrorReportDescription());
    }

    // region buttons

    @FXML
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.VIEWERRORREPORT, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_save(){
        // check if email has been entered and is valid.
        if (!EmailValidator.validateEmail(textField_email.getText())){
            new Alert(MainController.getInstance(), 5, AlertType.INFO,
                    "Den indtastet email er ikke valid!").start();
            return;
        }

        // checks if an item has been selected.
        if (inventoryItemsChoiceBox.getSelectionModel().getSelectedItem() == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO,
                    "Du har ikke valgt et objekt.").start();
            return;
        }

        // checks if an error description has been made.
        if (textArea_description.getText().length() <= 0){
            new Alert(MainController.getInstance(), 5, AlertType.INFO,
                    "Du har ikke skrevet en beskrivelse af fejlen!").start();
            return;
        }


        // saving selected item
        InventoryItems selectedInventoryItems = inventoryItemsChoiceBox.getSelectionModel().getSelectedItem();

        // saving description
        String description = textArea_description.getText();

        //saving room
        Room room = roomChoiceBox.getSelectionModel().getSelectedItem();

        errorReport.setErrorReportDescription(description);
        errorReport.setRoom(room);
        errorReport.setInventoryItems(selectedInventoryItems);

        DAO<ErrorReport> errorReportDAO = new ErrorReportDAODB();
        errorReportDAO.update(errorReport);

        // informing user that error report has been updated.
        new Alert(MainController.getInstance(), 5, AlertType.INFO, "Fejlmeldingen er nu " +
                "blevet updateret.").start();

    }

    // endregion

    @Override
    public void update(Object o) {

        // validating the object recived is of type error report.
        if (o instanceof ErrorReport){
            errorReport = (ErrorReport) o;
        }
        else {
            LoggerMessage.error(this, "In update; and object with the wrong type was published");
        }



    }
}
