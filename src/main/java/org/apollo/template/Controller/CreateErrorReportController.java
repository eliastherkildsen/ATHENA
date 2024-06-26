package org.apollo.template.Controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
import org.apollo.template.persistence.JDBC.StoredProcedure.AddEmailIfNotExists;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetEmailIDByEmailAdress;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateErrorReportController implements Initializable {

    @FXML
    private Button button_save, button_back;

    @FXML
    private TextField textField_email;

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

        // check if a room has been selected.
        if (roomChoiceBox.getSelectionModel().getSelectedItem() == null){
            new Alert(MainController.getInstance(), 5, AlertType.INFO,
                    "Du har ikke valgt et rum.").start();
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


        // saving email
        Email email = new Email(textField_email.getText());
        // creating email if not exists.
        AddEmailIfNotExists.addEmailIfNotExists(email);
        // saving selected item
        InventoryItems selectedInventoryItems = inventoryItemsChoiceBox.getSelectionModel().getSelectedItem();
        // saving description
        String description = textArea_description.getText();
        //saving room
        Room room = roomChoiceBox.getSelectionModel().getSelectedItem();

        // creating error report obj.
        ErrorReport errorReport = new ErrorReport(room, email, selectedInventoryItems, description, false, LocalDate.now());
        uploadErrorReport(errorReport);

        // informing user that error report has been created and sending user back.
        new Alert(MainController.getInstance(), 5, AlertType.INFO, "Fejlmeldingen er nu gemt.")
                .start();

    }

    /**
     * Method for uploading an errorport to the database.
     * @param errorReport ErrorReport.
     */
    private void uploadErrorReport(ErrorReport errorReport) {

        // get email id from emailName
        int emailID = GetEmailIDByEmailAdress.getEmailIDByEmailName(errorReport.getEmail());
        errorReport.getEmail().setEmailID(emailID);

        // storing the error report in the database.
        DAO<ErrorReport> dao = new ErrorReportDAODB();
        dao.add(errorReport);

    }


    /**
     * method for loading inventory items.
     */
    private void loadInventoryItems(){
        // clearing inventory choice box to prevent replica data.
        inventoryItemsChoiceBox.getItems().clear();

        // creating dao.
        DAO<InventoryItems> inventoryItemsDAO = new InventoryItemDAO();

        //loading items into choisbox
        inventoryItemsChoiceBox.getItems().addAll(inventoryItemsDAO.readAll());

    }

    /**
     * Method for loading room data.
     */
    private void loadRoomData() {
        // clearing room choice box to prevent replica data.
        roomChoiceBox.getItems().clear();

        // creating dao.
        DAO<Room> roomDAO = new RoomDAO();

        //loading items into choisbox
        roomChoiceBox.getItems().addAll(roomDAO.readAll());
    }

}
