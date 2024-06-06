package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Utility.TimeStrategyLogic;

import java.sql.Date;
import java.time.LocalDate;


public class DayStrategy implements TimeStrategy{

    // number of days used in the strategy by default
    private int FINAL_DAY = 1;


    /**
     * This method generates a StatObj based on a specific strategy and its corresponding number of days and a roomID
     * @param statisticsArea the strategy (time period) that should be used to generate the object.
     * @param roomID the roomID for a selected room
     * @return a StatObj that contains the variables needed to create a barChart
     */
    @Override
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID) {

        // gets today's date
        LocalDate currentDate = TimeStrategyLogic.getInstance().currentDate();

        // start- and end date is the same - that's why 2 x currentDate
        return TimeStrategyLogic.getInstance().generateStatObj(FINAL_DAY, Date.valueOf(currentDate), Date.valueOf(currentDate), statisticsArea, roomID);
    }

}
