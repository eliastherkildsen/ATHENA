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
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.Statistics.*;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.UI.AlertComp;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.RoomComp;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @FXML
    private ChoiceBox choiceBox_statisticArea, choiceBox_statisticPeriod;
    
    private Room selectedRoom = null;
    private List<RoomComp> roomComps = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoomVbox();
        loadAreaChoiceB();
        loadPeriodChoiceB();

    }


    private void loadAreaChoiceB() {

        choiceBox_statisticArea.getItems().addAll("Booket tid");
        choiceBox_statisticArea.setValue("Vælg område");
    }


    private void loadPeriodChoiceB() {

        choiceBox_statisticPeriod.getItems().addAll("I dag", "Sidste 7 dage", "Sidste måned");
        choiceBox_statisticPeriod.setValue("Vælg periode");
    }


    private StatisticsArea areaSelection(){

        int indexChosen = choiceBox_statisticArea.getSelectionModel().getSelectedIndex();
        StatisticsArea statisticsArea = null;

        if (indexChosen == 0) {
            statisticsArea = StatisticsArea.BOOKINGS;
        }

        return statisticsArea;
    }


    private int periodSelection(){
        int indexChosen = choiceBox_statisticPeriod.getSelectionModel().getSelectedIndex();
        int periodDays = 0;

        switch (indexChosen){

            // today
            case 0:
                periodDays = 1;
                break;

            // last 7 days
            case 1:
                periodDays = 7;
                break;

            // last month
            case 2:
                periodDays = 31;
                break;
        }

        return periodDays;
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


                    if (choiceBox_statisticArea.getSelectionModel().getSelectedItem() == null
                        || choiceBox_statisticPeriod.getSelectionModel().getSelectedItem() == null){

                        new Alert(MainController.getInstance(), 5, AlertType.INFO, "Husk at vælge statistikområde og statistikperiode").start();

                    } else {
                        // clear barChart
                        barChart_statistics.getData().clear();

                        int roomID = selectedRoom.getRoomID();
                        String statisticArea = choiceBox_statisticArea.getSelectionModel().getSelectedItem().toString();
                        String statisticStrategy = choiceBox_statisticPeriod.getSelectionModel().getSelectedItem().toString();


                        createGraph(roomID, statisticArea,statisticStrategy);
                    }

                }
            });

        }

        // adding roomcomponents to vbox_room
        vbox_room.getChildren().addAll(roomComps);

    }

    private void createGraph(int roomID, String statisticArea, String statisticStrategy) {

        TimeContext timeContext = new TimeContext();

        if (statisticArea.equals("Booket tid")){

            switch (statisticStrategy){
                case "I dag":
                    timeContext.setStrategy(new DayStrategy());
                    break;

                case "Sidste 7 dage":
                    timeContext.setStrategy(new WeekStrategy());
                    break;

                case "Sidste måned":
                    timeContext.setStrategy(new MonthStrategy());
                    break;
            }
        }

        StatObj statObj = timeContext.generateObj(StatisticsArea.BOOKINGS, roomID);

        barChart_statistics.getXAxis().setLabel(statObj.getxNotation());
        barChart_statistics.getYAxis().setLabel(statObj.getyNotation());
        barChart_statistics.setTitle(statObj.getGraphTitle());
        barChart_statistics.setData(statObj.getChartData());

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
