package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.AvailableRoom;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.AvailableComponent;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {

    //TODO: remember temporarily button in HomeController

    @FXML
    private VBox Vbox_Listview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // getting today's date
        Date dateToday = Date.valueOf(LocalDate.now());

        // search for available rooms today's date and saves them as a List
        List<AvailableRoom> roomsAvailableToday = storedSqlProcedure(dateToday);




        // TODO: custom component
        AvailableComponent availableComponent = new AvailableComponent("301", "3. sal", 25, "Projektor, Højtaler, Airtame");
        Vbox_Listview.getChildren().add(availableComponent);



    }


    // TODO: move to another class?

    /**
     * This method executes our stored SQL procedure "getAvailableRooms",
     * which finds all rooms with available booking times for today's date.
     * NOTE: We sort the data based on total booking time in our stored procedure.
     * - Rooms are considered available if they are not booked or if the total booking
     * time for today's date is < 480 minutes (8 hours).
     *
     * @param dateToday The today's date for which available rooms are being searched.
     * @return A List of AvailableRoom objects containing available rooms for today's date
     */
    private List<AvailableRoom> storedSqlProcedure (Date dateToday) {

        List<AvailableRoom> availableRooms = new ArrayList<>();

        try{
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getAvailableRooms @BookingDate = ?");
            ps.setDate(1, dateToday);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                AvailableRoom availableRoom = new AvailableRoom(rs.getString("fld_roomName"),
                                                                rs.getString("fld_floor"),
                                                                rs.getString("fld_roomTypeName"),
                                                                rs.getInt("fld_roomMaxPersonCount"));

                availableRooms.add(availableRoom);
            }

            return availableRooms;

        }catch (SQLException e){
            LoggerMessage.error(this,"Stored Procedure : getAvailableRooms didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
