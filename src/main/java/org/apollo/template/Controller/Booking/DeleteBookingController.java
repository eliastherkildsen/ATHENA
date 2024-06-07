package org.apollo.template.Controller.Booking;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.apollo.template.persistence.JDBC.StoredProcedure.LoadbookedRoomsByEmail.loadBookedRoomsByEmail;

public class DeleteBookingController implements BookingSelectionListner {

    @FXML
    private TextField textField_email;

    @FXML
    private VBox vbox_booking;

    private List<BookingComp> bookingCompList = new ArrayList<>();
    private int selectedBookingID = -1;

    private VBox vBox_sPaneBox;

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
            ScrollPane sPane = new ScrollPane(vBox_sPaneBox);
            sPane.getStyleClass().add("custom-scroll-pane");
            sPane.setFitToWidth(true);
            vbox_booking.getChildren().add(sPane);
            LoadBookedRooms.loadBookedRooms(loadBookedRoomsByEmail(email), vbox_booking, bookingCompList, this);
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




