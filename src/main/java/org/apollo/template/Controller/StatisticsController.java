package org.apollo.template.Controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.Statistics.*;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
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


    /**
     * This method loads the area choise box
     */
    private void loadAreaChoiceB() {

        choiceBox_statisticArea.getItems().addAll("Booket tid");
        choiceBox_statisticArea.setValue("Vælg område");
    }


    /**
     * This method loads the period choise box
     */
    private void loadPeriodChoiceB() {

        choiceBox_statisticPeriod.getItems().addAll("I dag", "Sidste 7 dage", "Sidste måned");
        choiceBox_statisticPeriod.setValue("Vælg periode");
    }


    /**
     * Loads room components into the VBox, clearing it first to avoid duplicates.
     * Creates a RoomComp for each room from the database, adds event handlers to handle selections,
     * and displays the corresponding graph when a room is selected.
     */
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

                    // this method creates and shows the barChart
                    showGraph();
                }
            });
        }

        // adding roomcomponents to vbox_room
        vbox_room.getChildren().addAll(roomComps);
    }


    /**
     * Method for displaying a graph based on the selected statistic area, statistic period and roomID
     * If no statistic area or statistic period is selected, it prompts the user to select them.
     */
    private void showGraph() {

            // checks if statistic area and statistic period is selected - if not
            if (choiceBox_statisticArea.getSelectionModel().getSelectedIndex() == -1
                || choiceBox_statisticPeriod.getSelectionModel().getSelectedIndex() == -1){

                new Alert(MainController.getInstance(), 5, AlertType.INFO, "Husk at vælge statistikområde og statistikperiode").start();
                LoggerMessage.info(this, "Need to select a statistic area and/or statistic period");

            // if selected
            } else {

                LoggerMessage.info(this, "All information is selected");

                // clear barChart
                barChart_statistics.getData().clear();

                try {
                    int roomID = selectedRoom.getRoomID();
                    String statisticArea = choiceBox_statisticArea.getSelectionModel().getSelectedItem().toString();
                    String statisticStrategy = choiceBox_statisticPeriod.getSelectionModel().getSelectedItem().toString();

                    createStatObj(roomID, statisticArea, statisticStrategy);
                    LoggerMessage.info(this, "Succeeded to create graph");

                } catch (Exception e){
                    LoggerMessage.error(this, "Failed to create graph");
                }
            }
    }


    /**
     * This method creates a StatObj based on the specified room ID, statistic area, and statistic strategy.
     * @param roomID the roomID for a selected room
     * @param statisticArea the area of statistics
     * @param statisticStrategy the strategy (time period) that should be used to generate the object
     */
    private void createStatObj(int roomID, String statisticArea, String statisticStrategy) {

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
            LoggerMessage.info(this, "Strategi: " + statisticStrategy);
        }

        StatObj statObj = timeContext.generateObj(StatisticsArea.BOOKINGS, roomID);
        generateBarChart(statObj);
    }


    /**
     * Method for generating the barChart using a StatObj
     * @param statObj a StatObj that contains the variables needed to create a barChart
     */
    private void generateBarChart(StatObj statObj) {

        try {
            // generates the barChart
            barChart_statistics.getXAxis().setLabel(statObj.getxNotation());
            barChart_statistics.getYAxis().setLabel(statObj.getyNotation());
            barChart_statistics.setTitle(statObj.getGraphTitle());
            barChart_statistics.setData(statObj.getChartData());

        } catch (Exception e) {
            LoggerMessage.error(this, "an error occurred: could not generate bar chart " + e.getMessage());
        }
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
