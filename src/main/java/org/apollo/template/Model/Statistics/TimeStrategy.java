package org.apollo.template.Model.Statistics;

public interface TimeStrategy {

    /**
     * This method generates a StatObj based on a specific strategy and a roomID
     * @param statisticsArea the strategy (time period) that should be used to generate the object.
     * @param roomID the roomID for a selected room
     * @return a StatObj that contains the variables needed to create a barChart.
     */
    StatObj generateObj(StatisticsArea statisticsArea, int roomID);

}
