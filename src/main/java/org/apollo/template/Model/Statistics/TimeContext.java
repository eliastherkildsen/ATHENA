package org.apollo.template.Model.Statistics;

public class TimeContext {

    private TimeStrategy strategy;


    public void setStrategy(TimeStrategy strategy) {
        this. strategy = strategy;
    }


    /** TODO: complete javaDoc
     * This method  ....
     * @param statisticsArea
     * @return
     */
    public StatObj generateObj(StatisticsArea statisticsArea, int roomID){
        if (strategy == null){
            throw new IllegalStateException("Strategy is not set");
        }
        return strategy.generateObj(statisticsArea, roomID);
    }

}
