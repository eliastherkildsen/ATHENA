package org.apollo.template.Model.Statistics;

public class Koordinates {

    // these are instance variables storing coordinates' x and y values
    private String xValue;
    private int yValue;

    public Koordinates(String xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

    // region getter og setter
    public String getxValue() {
        return xValue;
    }

    public void setxValue(String xValue) {
        this.xValue = xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public void setyValue(int yValue) {
        this.yValue = yValue;
    }

    // endregion
}