package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.sql.Date;
import java.time.LocalDate;


public class WeekStrategy implements TimeStrategy{

    // days in a week
    private int FINAL_DAYS_WEEK = 7;

    @Override
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID) {

        LocalDate currentDateLoc = LocalDate.now();

        Date startDate = Date.valueOf(TimeStrategyLogic.getInstance().startDate(currentDateLoc, FINAL_DAYS_WEEK));
        System.out.println("startDate: " + startDate);
        Date currentDate = Date.valueOf(LocalDate.now());

        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS_WEEK, startDate, currentDate, statisticsArea);

    }
}
