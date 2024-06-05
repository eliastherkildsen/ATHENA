package org.apollo.template.Service.Utility;

import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;

import java.time.LocalDate;

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




        return null;
    }

}
