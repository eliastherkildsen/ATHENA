package org.apollo.template.Service.Utility;

import org.apollo.template.Model.Statistics.StatObj;
import org.apollo.template.Model.Statistics.StatisticsArea;

public class TimeStrategyLogic {


    private static TimeStrategyLogic INSTANCE;

    private TimeStrategyLogic(){}

    public static TimeStrategyLogic getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TimeStrategyLogic();
        }
        return INSTANCE;
    }


    public StatObj generateStatObj(int noOfDaysToShow, StatisticsArea statisticsArea){
        return null;
    }

}
