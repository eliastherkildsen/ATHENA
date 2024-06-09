package org.apollo.template.Model.Statistics;

import org.apollo.template.Service.Logger.LoggerMessage;

public class TimeContext {


    private TimeStrategy strategy;

    public void setStrategy(TimeStrategy strategy) {

        this. strategy = strategy;
        LoggerMessage.info(this, "Strategy set to " + strategy);
    }


    /**
     * This method generates a StatObj based on the specified strategy and roomID.
     * @param statisticsArea the area of statistics to be generated
     * @return a StatObj that contains the variables needed to create a barChart
     */
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID){
        if (strategy == null){
            throw new IllegalStateException("Strategy is not set");
        }
        return strategy.generateObj(statisticsArea, roomID);
    }

}
