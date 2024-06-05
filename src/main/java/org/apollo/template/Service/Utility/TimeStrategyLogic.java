package org.apollo.template.Service.Utility;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;
import org.apollo.template.persistence.JDBC.StoredProcedure.GetTotBookingTimePerBok;

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
     * This method calculates the start date of a period given a current date and a number of days to go back.
     * @param currentDate the current date from which to calculate the start date
     * @param numberOfDays the number of days to subtract from the current date
     * @return the start date, which is the current date minus the specified number of days
     */
    public LocalDate startDate(LocalDate currentDate, int numberOfDays){

        return currentDate.minusDays(numberOfDays);
    }



    public StatObj generateStatObj(int numberOfDays, LocalDate startDate, LocalDate currentDate, StatisticsArea statisticsArea){

        String xNotation = null;
        String yNotation = null;
        String graphTitle = null;
        ObservableList<XYChart.Series<String, Number>> chartData = null;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){

            xNotation = getXNotation(numberOfDays);
            yNotation = getYNotation(StatisticsArea.PERSONKAPACITY, numberOfDays);
            graphTitle = getGraphTitle(StatisticsArea.PERSONKAPACITY, numberOfDays);
            chartData = getCharData(StatisticsArea.PERSONKAPACITY, numberOfDays, startDate, currentDate);

        } else{
            xNotation = getXNotation(numberOfDays);
            yNotation = getYNotation(StatisticsArea.BOOKINGS, numberOfDays);
            graphTitle = getGraphTitle(StatisticsArea.BOOKINGS, numberOfDays);
            chartData = getCharData(StatisticsArea.BOOKINGS, numberOfDays, startDate, currentDate);
        }

        return new StatObj(chartData, graphTitle, xNotation, yNotation);
    }



    private String getXNotation(int numberOfDays) {

        String xNotation = null;

        if (numberOfDays == 1) { xNotation = "Booking";}
        if (numberOfDays == 7 || numberOfDays == 31) { xNotation = "Dato";}
        if (numberOfDays == 365) { xNotation = "Måned";}

        return xNotation;
    }


    private String getYNotation(StatisticsArea statisticsArea, int numberOfDays) {

        String yNotation = null;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){

            if (numberOfDays == 1) { yNotation = "Antal deltagere";}
            if (numberOfDays == 7 || numberOfDays == 31 || numberOfDays == 365) { yNotation = "Gennemsnitlige antal deltagere pr. booking";}

        } else {
            if (numberOfDays == 1) { yNotation = "Bookingtid (minutter)";}
            if (numberOfDays == 7 || numberOfDays == 31 || numberOfDays == 365) { yNotation = "Summeret bookingtid (minutter)";}
        }

        return yNotation;
    }


    private String getGraphTitle(StatisticsArea statisticsArea, int numberOfDays) {

        String graphTitle = null;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){
            if (numberOfDays == 1) { graphTitle = "Antal deltagere pr. booking"; }
            if (numberOfDays == 7 || numberOfDays == 31 || numberOfDays == 365) { graphTitle = "Gennemsnitlige antal deltagere pr. booking"; }

        } else {
            if (numberOfDays == 1) { graphTitle = "Bookingtid pr. booking"; }
            if (numberOfDays == 7 || numberOfDays == 31) { graphTitle = "Summeret bookingtid pr. dag"; }
            if (numberOfDays == 365) {graphTitle = "Summeret bookingtid pr. måned"; }
        }

        return graphTitle;
    }


    private ObservableList<XYChart.Series<String, Number>> getCharData(StatisticsArea statisticsArea, int numberOfDays, LocalDate startDate, LocalDate currentDate) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){
            chartData = getPersonKapStatistics();
        } else {
            chartData = getBookingTimeStatistics(numberOfDays);
        }

        return chartData;
    }


    private ObservableList<XYChart.Series<String, Number>> getPersonKapStatistics() {

        // søgning - giver en liste

        // liste x
        // liste y


    }


    private ObservableList<XYChart.Series<String, Number>> getBookingTimeStatistics(int numberOfDays) {

        ObservableList<XYChart.Series<String, Number>> chartData = null;

        if (numberOfDays == 1){
            GetTotBookingTimePerBok
        }

        if (numberOfDays == 7 || numberOfDays == 31) {
            GetTotBookingTimeDay
        }

        if (numberOfDays == 365) {
            return null;
        }

        return chartData;
    }



}
