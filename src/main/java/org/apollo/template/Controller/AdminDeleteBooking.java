package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Utility.BookingSelectionListner;
import org.apollo.template.Service.Utility.LoadBookedRooms;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingInformationDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetBookingsByDate;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDeleteBooking implements Initializable, BookingSelectionListner{

    @FXML
    private VBox vbox_booking;
    @FXML
    private Button button_delete;

    private List<BookingComp> bookingComps = new ArrayList<>();
    private int selectedBookingID = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Date date = Date.valueOf(LocalDate.now());

        try {
            LoadBookedRooms.loadBookedRooms(GetBookingsByDate.loadBookedRoomsReturnRS(date), vbox_booking, bookingComps, this);
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
        BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setBookingId(selectedBookingID);

        // creating dao.
        DAO<BookingInformation> dao = new BookingInformationDAO();
        dao.delete(bookingInformation);
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