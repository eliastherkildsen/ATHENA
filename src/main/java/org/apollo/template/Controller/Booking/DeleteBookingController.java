package org.apollo.template.Controller.Booking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.*;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.BookingSelectionListner;
import org.apollo.template.Service.Utility.LoadBookedRooms;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static org.apollo.template.persistence.JDBC.StoredProcedure.LoadbookedRoomsByEmail.loadBookedRoomsByEmail;

public class DeleteBookingController implements Initializable, BookingSelectionListner {

    @FXML
    private TextField textField_email;

    @FXML
    private VBox vbox_booking;

    private List<BookingComp> bookingCompList = new ArrayList<>();
    private int selectedBookingID = -1;

    private HBox bookinComp;
    private ScrollPane sPane;
    private VBox vBox_sPaneBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox_sPaneBox = new VBox();
        generateSpaneVbox();
    }

    private void generateSpaneVbox(){
        sPane = new ScrollPane(vBox_sPaneBox);
        sPane.setMinHeight(550);
        sPane.getStyleClass().add("custom-scroll-pane");
        sPane.setFitToWidth(true);
    }

    // region buttons.
    @FXML
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_delete(){
        // checks if a booking has been selected.
        if (selectedBookingID == -1){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en booking!").start();
            return;
        }

        // creating bookingInformation obj.
        Booking booking = new Booking();
        booking.setBookingID(selectedBookingID);

        // creating dao.
        DAO<Booking> dao = new BookingDAO();
        dao.delete(booking);
        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu slettet bookingen").start();

        // Removes our BookingComp from vbox holding our search results.
        /*
        for (BookingComp bookingComp : bookingCompList) {
            if(bookingComp.getBookingID() == selectedBookingID){
                vbox_booking.getChildren().remove(bookingComp);
            }
        }
         */
        //Due to lack of time and faulty comp impementation.
        //this is the best implementation we are able to pull off for refreshing our search results after deleting.
        vbox_booking.getChildren().clear();
        vBox_sPaneBox.getChildren().clear();
        generateSpaneVbox();
        onButton_search();
    }

    @FXML
    protected void onButton_search(){
        // clearing the booking component list.
        bookingCompList.clear();
        // clearing the VBOX
        vbox_booking.getChildren().clear();

        String email = textField_email.getText();
        // validate email entered.
        if (!validateEnteredEmail(email)) return;

        // load booked rooms by email
        try {
//            ScrollPane sPane = new ScrollPane(vBox_sPaneBox);
//            vBox_sPaneBox = new VBox();
//            sPane.getStyleClass().add("custom-scroll-pane");
//            sPane.setMinHeight(550);
//            sPane.setFitToWidth(true);
            vbox_booking.getChildren().add(sPane);
            LoadBookedRooms.loadBookedRooms(loadBookedRoomsByEmail(email), vBox_sPaneBox, bookingCompList, this);
            LoggerMessage.debug(this,"Bookings Tied to email: " + bookingCompList.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Implementing the method from BookingSelectionListener interface
    @Override
    public void onBookingSelected(int bookingID) {
        System.out.println(bookingID);
        selectedBookingID = bookingID;

    }

    /**
     * Method for validating emails
     * @param text String
     * @return boolean
     */
    private boolean validateEnteredEmail(String text) {

        // check if an email has been entered.
        if (text.length() <= 0){
            new Alert(MainController.getInstance(),5, AlertType.INFO, "Du har ikke indtastet en email!" );
            return false;
        }

        // check if entered email is valid
        if (!EmailValidator.validateEmail(text)){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke indtastet en valid email!");
            return false;
        }

        return true;

    }

}

    // endregion




