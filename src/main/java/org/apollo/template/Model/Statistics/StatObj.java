package org.apollo.template.Model.Statistics;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class StatObj {

    // these are instance variables containing data for generating a barChart
    private ObservableList<XYChart.Series<String, Number>> chartData;
    private String graphTitle;
    private String xNotation;
    private String yNotation;

    public StatObj(ObservableList<XYChart.Series<String, Number>> chartData, String graphTitle, String xNotation, String yNotation) {
        this.chartData = chartData;
        this.graphTitle = graphTitle;
        this.xNotation = xNotation;
        this.yNotation = yNotation;
    }


    // region getter and setter
    public ObservableList<XYChart.Series<String, Number>> getChartData() {
        return chartData;
    }

    public void setChartData(ObservableList<XYChart.Series<String, Number>> chartData) {
        this.chartData = chartData;
    }

    public String getGraphTitle() {
        return graphTitle;
    }

    public void setGraphTitle(String graphTitle) {
        this.graphTitle = graphTitle;
    }

    public String getxNotation() {
        return xNotation;
    }

    public void setxNotation(String xNotation) {
        this.xNotation = xNotation;
    }

    public String getyNotation() {
        return yNotation;
    }

    public void setyNotation(String yNotation) {
        this.yNotation = yNotation;
    }

    // endregion


}
