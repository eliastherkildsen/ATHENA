package org.apollo.template.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.*;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.ReservedRoomsVBox;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.BookingInformationDAO;
import org.apollo.template.persistence.JDBC.DAO.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static org.apollo.template.persistence.JDBC.StoredProcedure.LoadbookedRoomsByEmail.loadBookedRoomsByEmail;

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
        System.out.println(email);

        // validate email entered.
        if (!validateEnteredEmail(email)) return;

        // load booked rooms by email
        try {
            loadBookedRooms(loadBookedRoomsByEmail(email));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        List<Booking> bookingList = new ArrayList<>();
        while (rs.next()){
            int bookingId = Integer.parseInt(rs.getString("fld_bookingID"));

            Room room = new Room();
            room.setRoomName(rs.getString("fld_roomName"));

            MeetingType meetingType = new MeetingType(rs.getString("fld_meetingType"));

            String username = rs.getString("fld_userName");
            String start = rs.getString("fld_startTime");
            String end = rs.getString("fld_endTime");

            Time startTime = rs.getTime("fld_startTime");
            Time endTime = rs.getTime("fld_endTime");

            //Creating our information holder object with data from the DB/search
            Booking booking = new Booking();
            booking.setRoom(room);
            booking.setUsername(username);
            booking.setMeetingType(meetingType);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);

            bookingList.add(booking);
        }

        //Creating our Component using the list of information we generated above.
        ReservedRoomsVBox reservedRoomsVBox = new ReservedRoomsVBox(bookingList);

        //Getting our add action on our components
        for (BookingComp bookingComp : reservedRoomsVBox.getBookingComps()) {
            attatchOnAction(bookingComp);
            bookingCompList.add(bookingComp);
        }

        //Adding our Vbox of bookings to the container we want them displayed in.
        vbox_booking.getChildren().add(reservedRoomsVBox);
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
                bookingComp.setBookingCompColor(CompColors.SELECTED);

                LoggerMessage.debug(this, "selectedBookingID: " + selectedBookingID);
            }
        });

    }

    /**
     * Method for setting all booking components color to NORMAL.
     */
    private void unselectAllBookingComp() {
        for (BookingComp bookingComp : bookingCompList) {
            bookingComp.setBookingCompColor(CompColors.NORMAL);
        }
    }


}

    // endregion




