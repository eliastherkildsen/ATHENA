package org.apollo.template.Model.Statistics;

public class Koordinates {

    private String xValue;
    private int yValue;

    public Koordinates(String xValue, int yValue) {
        this.xValue = xValue;
        this.yValue = yValue;
    }

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
}