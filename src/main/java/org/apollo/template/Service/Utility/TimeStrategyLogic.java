package org.apollo.template.Service.Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apollo.template.Model.Statistics.Koordinates;
import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetTotBookingTimePerBok;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetTotBookingTimePerDay;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class TimeStrategyLogic {

    // Singleton
    private static TimeStrategyLogic INSTANCE;

    private TimeStrategyLogic(){}

    public static TimeStrategyLogic getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TimeStrategyLogic();
        }
        return INSTANCE;
    }


    /**
     * This method returns today's date
     * @return today's date
     */
    public LocalDate currentDate(){
        return LocalDate.now();
    }


    /**
     * This method calculates the start date of a period given a current date and a number of days to go back
     * @param currentDate today's date from which to calculate the start date
     * @param numberOfDays the number of days to subtract from the current date
     * @return the start date, which is the current date minus the specified number of days
     */
    public LocalDate startDate(LocalDate currentDate, int numberOfDays){
        return currentDate.minusDays(numberOfDays);
    }


    /**
     * This method generates a StatObj for a selected room within a given strategy period to be used in a bar chart
     * @param numberOfDays number of days used in the strategy
     * @param startDate the start date from which to retrieve total booking time per day
     * @param currentDate today's date
     * @param statisticsArea the area of statistics to be generated
     * @param roomID the roomID for a selected room
     * @return a StatObj that contains the variables needed to create a barChart
     */
    public StatObj generateStatObj(int numberOfDays, Date startDate, Date currentDate, StatisticsArea statisticsArea, int roomID){

        LoggerMessage.debug("TimeStrategyLogic", String.format("StatisticArea: %s - Start date: %s - Current date: %s",statisticsArea,startDate,currentDate));

        String xNotation = null;
        String yNotation = null;
        String graphTitle = null;
        ObservableList<XYChart.Series<String, Number>> chartData = null;

            xNotation = getXNotation(numberOfDays);
            yNotation = getYNotation(numberOfDays);
            graphTitle = getGraphTitle(numberOfDays);
            chartData = getCharData(numberOfDays, startDate, currentDate, roomID);

        return new StatObj(chartData, graphTitle, xNotation, yNotation);
    }


    /**
     * This method retrieves the bar chart's x-axis notation based on the strategy (time period)
     * @param numberOfDays number of days used in the strategy
     * @return the x-axis notation to be used in the bar chart
     */
    private String getXNotation(int numberOfDays) {

        String xNotation = null;

        if (numberOfDays == 1) { xNotation = "Booking";}
        if (numberOfDays == 7 || numberOfDays == 31) { xNotation = "Dato";}

        return xNotation;
    }


    /**
     * This method retrieves the bar chart's y-axis notation based on the strategy (time period)
     * @param numberOfDays number of days used in the strategy
     * @return the y-axis notation to be used in the bar chart
     */
    private String getYNotation(int numberOfDays) {

        String yNotation = null;

            if (numberOfDays == 1) { yNotation = "Bookingtid (minutter)";}
            if (numberOfDays == 7 || numberOfDays == 31) { yNotation = "Summeret bookingtid (minutter)";}

        return yNotation;
    }


    /**
     * This method retrieves the bar chart's graph title notation based on the strategy (time period)
     * @param numberOfDays number of days used in the strategy
     * @return the graph title notation to be used in the bar chart
     */
    private String getGraphTitle(int numberOfDays) {

        String graphTitle = null;

            if (numberOfDays == 1) { graphTitle = "Bookingtid pr. booking"; }
            if (numberOfDays == 7 || numberOfDays == 31) { graphTitle = "Summeret bookingtid pr. dag"; }

        return graphTitle;
    }


    /**
     * Method that retrieves all the char data information for a selected room within a given strategy period to be used in a bar chart
     * @param numberOfDays number of days used in the strategy
     * @param startDate the start date from which to retrieve total booking time per day
     * @param currentDate today's date
     * @param roomID the roomID for a selected room
     * @return an ObservableList of XYChart.Series containing the chart data
     */
    private ObservableList<XYChart.Series<String, Number>> getCharData(int numberOfDays, Date startDate, Date currentDate, int roomID) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

        try {
            chartData = getBookingTimeStatistics(numberOfDays, startDate, currentDate, roomID);
        } catch (Exception e){
            LoggerMessage.error("TimeStrategyLogic", "an error occurred getting chart data " + e.getMessage());
        }

        return chartData;
    }


    /**
     * This method retrieves the booking time statistics for a selected room within a given strategy period
     * @param numberOfDays number of days used in the strategy
     * @param startDate the start date from which to retrieve total booking time per day
     * @param currentDate today's date
     * @param roomID the roomID for a selected room
     * @return an ObservableList of XYChart.Series containing the chart data with booking time statistics
     */
    private ObservableList<XYChart.Series<String, Number>> getBookingTimeStatistics(int numberOfDays, Date startDate, Date currentDate, int roomID) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

        if (numberOfDays == 1){
            List<Koordinates> koordinates = GetTotBookingTimePerBok.getTotalBookingTime(roomID, currentDate);
            chartData = buildObsList(koordinates);
        }

        if (numberOfDays == 7 || numberOfDays == 31) {
            List<Koordinates> koordinates = GetTotBookingTimePerDay.getTotalBookingDay(roomID, startDate, currentDate);
            chartData = buildObsList(koordinates);
        }

        return chartData;
    }


    /**
     * Method for building an ObservableList of XYChart.Series from a list of coordinates
     * @param koordinates the list of coordinates used to create the chart series
     * @return an ObservableList of XYChart.Series containing the chart data
     */
    private ObservableList<XYChart.Series<String, Number>> buildObsList(List<Koordinates> koordinates) {

        XYChart.Series<String, Number> seriesDay = createSerie(koordinates);

        ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();
        chartData.add(seriesDay);
        return chartData;
    }


    /**
     * Method for creating a XYChart.Series given a list of coordinates
     * @param koordinates the list of coordinates used to create the chart series
     * @return a XYChart.Series containing the coordinates
     */
    private XYChart.Series<String, Number> createSerie(List<Koordinates> koordinates) {

        XYChart.Series<String, Number> seriesDay = new XYChart.Series<>();
        seriesDay.setName("Booking tid i minutter");

        for (Koordinates koordinate : koordinates) {
            seriesDay.getData().add(new XYChart.Data<>(koordinate.getxValue(), koordinate.getyValue()));
        }

        return seriesDay;
    }


}
