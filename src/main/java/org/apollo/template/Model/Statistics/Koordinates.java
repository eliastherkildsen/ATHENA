package org.apollo.template.Model.Statistics;

public class Koordinates {

    private String objectID;
    private int value;

    public Koordinates(String objectID, int value) {
        this.objectID = objectID;
        this.value = value;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
