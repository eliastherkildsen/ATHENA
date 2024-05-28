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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AvailableRoomsController implements Initializable {

    //TODO: remember temporarily button in HomeController

    @FXML
    private VBox Vbox_Listview;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // getting today's date
        Date dateToday = Date.valueOf(LocalDate.now());

        // custom component
        AvailableComponent availableComponent = new AvailableComponent("301", "3. sal", 25, "Projektor, Højtaler, Airtame");
        Vbox_Listview.getChildren().add(availableComponent);

        // search for available rooms today's date
        storedSqlProcedure(dateToday);
        //TODO: List<AvailableRoom> availableRooms =
    }

    private void storedSqlProcedure (Date dateToday) {
    // TODO: List<AvailableRoom>
        try{
            PreparedStatement ps = JDBC.get().getConnection().prepareStatement("EXECUTE getAvailableRooms @BookingDate = ?");
            ps.setDate(1, dateToday);

        }catch (SQLException e){
            LoggerMessage.error(this,"Stored Procedure : getAvailableRooms didn't run as intended " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
