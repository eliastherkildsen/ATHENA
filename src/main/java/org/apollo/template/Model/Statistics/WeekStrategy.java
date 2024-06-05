package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;
import java.time.LocalDate;


public class WeekStrategy implements TimeStrategy{

    // days in a week
    private int FINAL_DAYS_WEEK = 7;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea) {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = TimeStrategyLogic.getInstance().startDate(currentDate, FINAL_DAYS_WEEK);

        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS_WEEK, startDate, currentDate, statisticsArea);

    }
}
