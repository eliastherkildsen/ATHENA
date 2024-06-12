package org.apollo.template.View.UI;

public enum CompColors {

    SELECTED("#FBBB2C"),
    NORMAL("#009FE3"),
    ARCHIVED("#D9D9D9");

    private String color;

    CompColors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

}
