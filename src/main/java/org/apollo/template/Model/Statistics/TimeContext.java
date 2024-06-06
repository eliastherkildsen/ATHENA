package org.apollo.template.Model.Statistics;

public class TimeContext {

    // TODO: LOGMESSAGES


    private TimeStrategy strategy;


    public void setStrategy(TimeStrategy strategy) {
        this. strategy = strategy;
    }


    /**
     * This method generates a StatObj based on the specified strategy and roomID.
     * @param statisticsArea the strategy (time period) that should be used to generate the object
     * @return a StatObj that contains the variables needed to create a barChart
     */
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID){
        if (strategy == null){
            throw new IllegalStateException("Strategy is not set");
        }
        return strategy.generateObj(statisticsArea, roomID);
    }

}
