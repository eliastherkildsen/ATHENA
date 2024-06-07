package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.Service.Utility.TimeStrategyLogic;
import java.sql.Date;
import java.time.LocalDate;


public class MonthStrategy implements TimeStrategy{

    // number of days used in the strategy by default (31 = days in a month)
    private int FINAL_DAYS_MONTH = 31;


    /**
     * This method generates a StatObj based on a specific strategy and its corresponding number of days and a roomID
     * @param statisticsArea the strategy (time period) that should be used to generate the object
     * @param roomID the roomID for a selected room
     * @return a StatObj that contains the variables needed to create a barChart
     */
    @Override
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID) {

        // gets today's date
        LocalDate currentDate = TimeStrategyLogic.getInstance().currentDate();
        LoggerMessage.debug(this, "Current Date: " + LocalDate.now());

        // calculates the start date based on today's date given the number of days used in this strategy
        Date startDate = Date.valueOf(TimeStrategyLogic.getInstance().startDate(currentDate, FINAL_DAYS_MONTH));
        LoggerMessage.debug(this, "Start Date: " + startDate);

        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAYS_MONTH, startDate, Date.valueOf(currentDate), statisticsArea, roomID);
    }
}
