package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.sql.Date;
import java.time.LocalDate;


public class MonthStrategy implements TimeStrategy{

    // days in a month
    private int FINAL_DAYS_MONTH = 31;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID) {


        LocalDate currentDateLoc = LocalDate.now();

        Date startDate = Date.valueOf(TimeStrategyLogic.getInstance().startDate(currentDateLoc, FINAL_DAYS_MONTH));
        Date currentDate = Date.valueOf(LocalDate.now());


        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS_MONTH, startDate, currentDate, statisticsArea);
    }
}
