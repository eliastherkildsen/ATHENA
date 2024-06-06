package org.apollo.template.Service.Utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apollo.template.Model.Statistics.Koordinates;
import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetTotBookingTimePerBok;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetTotBookingTimePerDay;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class TimeStrategyLogic {

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
     * @return
     */
    public LocalDate currentDate(){
        return LocalDate.now();
    }



    /**
     * This method calculates the start date of a period given a current date and a number of days to go back.
     * @param currentDate the current date from which to calculate the start date
     * @param numberOfDays the number of days to subtract from the current date
     * @return the start date, which is the current date minus the specified number of days
     */
    public LocalDate startDate(LocalDate currentDate, int numberOfDays){
        System.out.println("number of days: " + numberOfDays);
        return currentDate.minusDays(numberOfDays);
    }



    public StatObj generateStatObj(int numberOfDays, Date startDate, Date currentDate, StatisticsArea statisticsArea, int roomID){

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



    private String getXNotation(int numberOfDays) {

        String xNotation = null;

        if (numberOfDays == 1) { xNotation = "Booking";}
        if (numberOfDays == 7 || numberOfDays == 31) { xNotation = "Dato";}

        return xNotation;
    }


    private String getYNotation(int numberOfDays) {

        String yNotation = null;

            if (numberOfDays == 1) { yNotation = "Bookingtid (minutter)";}
            if (numberOfDays == 7 || numberOfDays == 31) { yNotation = "Summeret bookingtid (minutter)";}

        return yNotation;
    }


    private String getGraphTitle(int numberOfDays) {

        String graphTitle = null;

            if (numberOfDays == 1) { graphTitle = "Bookingtid pr. booking"; }
            if (numberOfDays == 7 || numberOfDays == 31) { graphTitle = "Summeret bookingtid pr. dag"; }

        return graphTitle;
    }


    private ObservableList<XYChart.Series<String, Number>> getCharData(int numberOfDays, Date startDate, Date currentDate, int roomID) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

            chartData = getBookingTimeStatistics(numberOfDays, startDate, currentDate, roomID);

        return chartData;
    }


    private ObservableList<XYChart.Series<String, Number>> getBookingTimeStatistics(int numberOfDays, Date startDate, Date currentDate, int roomID) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

        //TODO: hardCoded!
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

    private ObservableList<XYChart.Series<String, Number>> buildObsList(List<Koordinates> koordinates) {

        XYChart.Series<String, Number> seriesDay = createSerie(koordinates);

        ObservableList<XYChart.Series<String, Number>> chartData = FXCollections.observableArrayList();
        chartData.add(seriesDay);
        return chartData;
    }

    private XYChart.Series<String, Number> createSerie(List<Koordinates> koordinates) {

        XYChart.Series<String, Number> seriesDay = new XYChart.Series<>();
        // TODO: variabel navn der bliver sendt med
        seriesDay.setName("Booking tid i minutter");


        for (Koordinates koordinate : koordinates) {
            seriesDay.getData().add(new XYChart.Data<>(koordinate.getxValue(), koordinate.getyValue()));
            System.out.println("koordinate x: " + koordinate.getxValue());
            System.out.println("koordinate y" + koordinate.getyValue());
        }

        return seriesDay;
    }


}
