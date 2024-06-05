package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.time.LocalDate;

public class DayStrategy implements TimeStrategy{

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea) {

        LocalDate currentDate = LocalDate.now();

        // start- and end date is the same
        return TimeStrategyLogic.getInstance().generateStatObj(currentDate, currentDate, statisticsArea);
    }
}
