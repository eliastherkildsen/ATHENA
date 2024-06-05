package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.time.LocalDate;

public class MonthStrategy implements TimeStrategy{

    // days in a month
    private int FINAL_DAYS_MONTH = 31;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea) {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = TimeStrategyLogic.getInstance().startDate(currentDate, FINAL_DAYS_MONTH);

        return TimeStrategyLogic.getInstance().generateStatObj(startDate, currentDate, statisticsArea);
    }
}
