package org.apollo.template.Model.Statistics;

public class Koordinates {

    private int objectID;
    private int value;

    public Koordinates(int objectID, int value) {
        this.objectID = objectID;
        this.value = value;
    }

    public int getObjectID() {
        return objectID;
    }

    public void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
