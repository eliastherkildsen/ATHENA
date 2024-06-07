package org.apollo.template.Controller.Booking;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Booking;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Utility.BookingSelectionListner;
import org.apollo.template.Service.Utility.LoadBookedRooms;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetBookingsByDate;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDeleteBookingController implements Initializable, BookingSelectionListner{

    @FXML
    private VBox vbox_booking;

    private List<BookingComp> bookingComps = new ArrayList<>();
    private int selectedBookingID = -1;

    private VBox vBox_sPaneBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Date date = Date.valueOf(LocalDate.now());

        ScrollPane sPane = new ScrollPane(vBox_sPaneBox);
        sPane.getStyleClass().add("custom-scroll-pane");
        sPane.setFitToWidth(true);
        vbox_booking.getChildren().add(sPane);


        try {
            // Loads all bookings from today and going forward
            LoadBookedRooms.loadBookedRooms(GetBookingsByDate.loadBookedRoomsReturnRS(date), vBox_sPaneBox, bookingComps, this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.ADMINVIEWBOOKINGS, BorderPaneRegion.CENTER);
    }

    @Override
    public void onBookingSelected(int bookingID) {
        selectedBookingID = bookingID;
    }
}
