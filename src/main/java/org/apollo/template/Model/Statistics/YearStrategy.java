package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;
import java.time.LocalDate;


public class YearStrategy implements TimeStrategy {

    // days in a year
    private int FINAL_DAYS_YEAR = 365;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea) {

        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = TimeStrategyLogic.getInstance().startDate(currentDate, FINAL_DAYS_YEAR);

        return TimeStrategyLogic.getInstance().generateStatObj(startDate, currentDate, statisticsArea);
    }
}
