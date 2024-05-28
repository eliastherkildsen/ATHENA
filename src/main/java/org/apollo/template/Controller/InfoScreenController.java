package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Model.BookingInformation;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.UI.ReservedRoomsVBox;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class InfoScreenController implements Initializable {

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoggerMessage.info(this,"InfoView initialized");
        Date newdate = Date.valueOf("2024-05-27");
        List<BookingInformation> booking = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            BookingInformation book = new BookingInformation("10:00", "12:00", newdate, 1, 10, "Alexander Alexandersen", 1,1,1,1,1);
            booking.add(book);
        }

        ReservedRoomsVBox vboxRooms = new ReservedRoomsVBox(booking);

        //Setting up ScrolLPane
        ScrollPane spane = new ScrollPane();
        spane.setFitToWidth(true);
        spane.getStyleClass().add("edge-to-edge"); //Remove 'edge' around scrollPane
        spane.setContent(vboxRooms);

        //MinMax required on root to display all information correctly.
        root.setMinHeight(700);
        root.setMinWidth(800);
        root.getChildren().add(spane);

        //Setting ScrollPane according to AnchorPane to display properly
        root.setTopAnchor(spane,0.0);
        root.setRightAnchor(spane,0.0);
        root.setLeftAnchor(spane,0.0);
        root.setBottomAnchor(spane,0.0);

    }


    // endregion


}
