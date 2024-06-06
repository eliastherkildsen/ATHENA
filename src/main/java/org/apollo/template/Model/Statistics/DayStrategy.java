package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.sql.Date;
import java.time.LocalDate;


public class DayStrategy implements TimeStrategy{

    private int FINAL_DAYS = 1;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID) {

        Date currentDate = Date.valueOf(LocalDate.now());

        // start- and end date is the same -> 2 x currentDate
        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS, currentDate, currentDate, statisticsArea);
    }

}
