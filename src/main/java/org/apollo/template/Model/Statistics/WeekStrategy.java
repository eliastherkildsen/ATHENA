package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

public class WeekStrategy implements TimeStrategy{

    private int FINAL_DAYS_WEEK = 7;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea) {

        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS_WEEK, statisticsArea);

    }
}
