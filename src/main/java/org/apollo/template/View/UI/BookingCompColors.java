package org.apollo.template.View.UI;

public enum BookingCompColors {

    SELECTED("#FBBB2C"),
    NORMAL("#009FE3");

    private String color;

    BookingCompColors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
