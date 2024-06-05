package org.apollo.template.Service.Utility;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;
import java.time.LocalDate;
import java.util.IllegalFormatCodePointException;

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



    public StatObj generateStatObj(LocalDate startDate, LocalDate currentDate, StatisticsArea statisticsArea){

        String xNotation = null;
        String yNotation = null;
        String graphTitle = null;
        ObservableList<XYChart.Series<String, Number>> chartData = null;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){

            xNotation = getXNotation(StatisticsArea.PERSONKAPACITY);
            yNotation = getYNotation(StatisticsArea.PERSONKAPACITY);
            graphTitle = getGraphTitle(StatisticsArea.PERSONKAPACITY);
            chartData = getCharData(StatisticsArea.PERSONKAPACITY);

        } else{
            xNotation = getXNotation(StatisticsArea.BOOKINGS);
            yNotation = getYNotation(StatisticsArea.BOOKINGS);
            graphTitle = getGraphTitle(StatisticsArea.BOOKINGS);
            chartData = getCharData(StatisticsArea.BOOKINGS);
        }

        return new StatObj(chartData, graphTitle, xNotation, yNotation);
    }



    private String getXNotation(StatisticsArea statisticsArea) {

        String xNotation;

        if (statisticsArea == StatisticsArea.PERSONKAPACITY){




        }else {

        }

        return xNotation;
    }


    private String getYNotation(StatisticsArea statisticsArea) {
    }


    private String getGraphTitle(StatisticsArea statisticsArea) {
    }


    private ObservableList<XYChart.Series<String, Number>> getCharData(StatisticsArea statisticsArea) {
    }

}
