package org.apollo.template.Controller;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.EmailValidator;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.BookingComp;
import org.apollo.template.View.ViewList;

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

    // region buttons.
    @FXML
    protected void onButton_back(){
        MainController.getInstance().setView(ViewList.INFOSCREEN, BorderPaneRegion.CENTER);
    }

    @FXML
    protected void onButton_delete(){

    }

    @FXML
    protected void onButton_search(){

        // check if an email has been entered.
        if (textField_email.getText().length() <= 0){
            new Alert(MainController.getInstance(),5, AlertType.INFO, "Du har ikke indtastet en email!" );
            return;
        }

        // check if entered email is valid
        if (!EmailValidator.validateEmail(textField_email.getText())){
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke indtastet en valid email!");
            return;
        }

        ResultSet rs;

        // load all bookings from user email
        try {
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXEC FindbookingByEmail @EmailAddress = ? ");
            ps.setString(1, textField_email.getText());
            rs = ps.executeQuery();
            loadBookedRooms(rs);
        } catch (SQLException e) {
            LoggerMessage.error(this, "in onButton_search; An error occurred " + e.getMessage());
        }




    }

    private void loadBookedRooms(ResultSet rs) throws SQLException {

        while (rs.next()){

            String roomName = rs.getString("fld_roomName");
            String username = rs.getString("fld_userName");
            String meetingType = rs.getString("fld_meetingType");
            String startAndEndTime = rs.getString("fld_startTime");
            startAndEndTime += " " + rs.getString("fld_endTime");

            bookingCompList.add(new BookingComp(roomName, username, meetingType, startAndEndTime));

        }


        vbox_booking.getChildren().addAll(bookingCompList);
    }


}

    // endregion




