package org.apollo.template.Controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.apollo.template.Model.Email;
import org.apollo.template.Model.InventoryItems;
import org.apollo.template.Model.Room;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.InventoryItemDAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAODB;

import java.net.URL;
import java.util.ResourceBundle;

public class ErrorReportController implements Initializable {

    @FXML
    private Button button_add, button_remove, button_save, button_back;

    @FXML
    private TextField textField_name, textField_email;

    @FXML
    private ChoiceBox<InventoryItems> inventoryItemsChoiceBox;

    @FXML
    private ChoiceBox<Room> roomChoiceBox;

    @FXML
    private ListView<InventoryItems> inventoryItemsListView;

    @FXML
    private Label lable_totalChar, lable_maxChar;

    @FXML
    private TextArea textArea_description;

    private String name;
    private Email email;
    private final int MAX_CHARS = 500;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // setting lable for max characters in the description text area.
        lable_maxChar.setText("/" + MAX_CHARS + " Tegn");

        // databinding textArea_description length to lable_totalChar.
        lable_totalChar.textProperty().bind(
                Bindings.createStringBinding(
                        () -> "" + textArea_description.getText().length(),
                        textArea_description.textProperty()
                )
        );
        // load inventory cb
        loadInventoryItems();

        // load room cb
        loadRoomData();
    }


    @FXML
    protected void onButton_back(){
        LoggerMessage.error(this, "button_back needs to be linked to the right view");
        MainController.getInstance().setView(ViewList.HOME, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_save(){

        // check if a name has been entered.
        if (textField_name.getText().length() <= 0){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Der er ikke indtastet et navn på andmelder!").start();
            return;
        }

        // check if email has been entered and is valid.
        if (!EmailValidator.validateEmail(textField_email.getText())){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Den indtastet email er ikke valid!").start();
            return;
        }

        // saving email
        email = new Email(textField_email.getText());
        // saving name.
        name = textField_name.getText();

    }

    @FXML
    protected void onButton_add(){

        // checks if an item has been selected.
        if (inventoryItemsChoiceBox.getSelectionModel().getSelectedItem() == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt et objekt.").start();
            return;
        }

        // checks if the item has allready been added,
        if (inventoryItemsListView.getItems().contains(inventoryItemsChoiceBox.getSelectionModel().getSelectedItem())){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Dette objekt er allerede tilføjet!.").start();
            return;
        }

        // addes the item to listview.
        inventoryItemsListView.getItems().add(inventoryItemsChoiceBox.getSelectionModel().getSelectedItem());


    }

    @FXML
    protected void onButton_remove(){
        // checks if an item has been selected.
        if (inventoryItemsListView.getSelectionModel().getSelectedItem() == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt et objekt.").start();
            return;
        }

        inventoryItemsListView.getItems().remove(inventoryItemsListView.getSelectionModel().getSelectedItem());
    }


    // method for loading inventory items.
    private void loadInventoryItems(){
        // clearing inventory choice box to prevent replica data.
        inventoryItemsChoiceBox.getItems().clear();

        // creating dao.
        DAO<InventoryItems> inventoryItemsDAO = new InventoryItemDAO();

        //loading items into choisbox
        inventoryItemsChoiceBox.getItems().addAll(inventoryItemsDAO.readAll());

    }

    private void loadRoomData() {
        // clearing room choice box to prevent replica data.
        roomChoiceBox.getItems().clear();

        // creating dao.
        DAO<Room> roomDAO = new RoomDAODB();

        //loading items into choisbox
        roomChoiceBox.getItems().addAll(roomDAO.readAll());
    }

}
