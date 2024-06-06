package org.apollo.template.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.Statistics.DayStrategy;
import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;
import org.apollo.template.Model.Statistics.TimeContext;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.RoomComp;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
    @FXML
    private BarChart barChart_statistics;
    @FXML
    private CategoryAxis barChart_xAxis;
    @FXML
    private NumberAxis barChart_yAxis;
    @FXML
    private VBox vbox_room;
    private Room selectedRoom = null;
    private List<RoomComp> roomComps = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoomVbox();


        TimeContext timeContext = new TimeContext();
        timeContext.setStrategy(new DayStrategy());

        StatObj statObj = timeContext.generateObj(StatisticsArea.BOOKINGS);


        barChart_statistics.getXAxis().setLabel(statObj.getxNotation());
        barChart_statistics.getYAxis().setLabel(statObj.getyNotation());
        barChart_statistics.setTitle(statObj.getGraphTitle());
        barChart_statistics.setData(statObj.getChartData());

    }




    private void loadRoomVbox() {

        // clearing vbox to avoid replica data.
        vbox_room.getChildren().clear();

        // load all rooms from database.
        DAO<Room> roomDAO = new RoomDAO();
        List<Room> roomList = roomDAO.readAll();

        // create room component for all rooms.
        for (Room room : roomList) {

            // creating the room component.
            RoomComp roomComp = new RoomComp(room);

            // adding roomComp to list for easy iteration when unselecting.
            roomComps.add(roomComp);

            // attatching on action
            roomComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // unselect all components, to make sure that only one component is seleceted at a time.
                    unselectAllRoomComponents();
                    // changing roomComp color
                    roomComp.setCompColors(CompColors.SELECTED);
                    // settinge the selected room
                    selectedRoom = room;
                }
            });

        }

        // adding roomcomponents to vbox_room
        vbox_room.getChildren().addAll(roomComps);

    }

    /**
     * Method for setting all room components colors to NORMAL.
     */
    private void unselectAllRoomComponents() {
        for (RoomComp roomComp : roomComps) {
            roomComp.setCompColors(CompColors.NORMAL);
        }
    }
}
