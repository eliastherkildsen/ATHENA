package org.apollo.template.Controller;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.UI.BookingCompColors;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.BookingInformationDAO;
import org.apollo.template.persistence.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteBookingController {

    @FXML
    private Button button_delete, button_back, button_search;

    @FXML
    private TextField textField_email;

    @FXML
    private VBox vbox_booking;

    private List<BookingComp> bookingCompList = new ArrayList<>();
    private int selectedBookingID = -1;

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
        BookingInformation bookingInformation = new BookingInformation();
        bookingInformation.setBookingId(selectedBookingID);

        // creating dao.
        DAO<BookingInformation> dao = new BookingInformationDAO();
        dao.delete(bookingInformation);
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
        loadBookedRoomsByEmail(email);

    }

    /**
     * Method for findeing bookings by email. calls method for loading thies,
     * @param email String
     */
    private void loadBookedRoomsByEmail(String email) {
        ResultSet rs;

        // load all bookings from user email
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC FindbookingByEmail @EmailAddress = ? ");
            ps.setString(1, email);
            rs = ps.executeQuery();
            loadBookedRooms(rs);
            rs.close();
        } catch (SQLException e) {
            LoggerMessage.error(this, "in onButton_search; An error occurred " + e.getMessage());
        }
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

    /**
     * method for loading into booked rooms into a Vbox
     * @param rs ResultSet
     * @throws SQLException
     */
    private void loadBookedRooms(ResultSet rs) throws SQLException {

        while (rs.next()){

            int bookingId = Integer.parseInt(rs.getString("fld_bookingID"));
            String roomName = rs.getString("fld_roomName");
            String username = rs.getString("fld_userName");
            String meetingType = rs.getString("fld_meetingType");
            String startAndEndTime = rs.getString("fld_startTime");
            startAndEndTime += " " + rs.getString("fld_endTime");

            BookingComp bookingComp = new BookingComp(roomName, username, meetingType, startAndEndTime, bookingId);
            attatchOnAction(bookingComp);

            bookingCompList.add(bookingComp);

        }


        vbox_booking.getChildren().addAll(bookingCompList);
    }

    /**
     * Method for attaching MouseEvent handler to booking comp. and logic for selecting.
     * @param bookingComp BookingComp
     */
    private void attatchOnAction(BookingComp bookingComp) {

        bookingComp.setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // unselect all booking comps.
                unselectAllBookingComp();
                selectedBookingID = bookingComp.getBookingID();
                bookingComp.setBookingCompColor(BookingCompColors.SELECTED);

                LoggerMessage.debug(this, "selectedBookingID: " + selectedBookingID);
            }
        });

    }

    /**
     * Method for setting all booking components color to NORMAL.
     */
    private void unselectAllBookingComp() {
        for (BookingComp bookingComp : bookingCompList) {
            bookingComp.setBookingCompColor(BookingCompColors.NORMAL);
        }
    }


}

    // endregion




