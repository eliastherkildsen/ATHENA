package org.apollo.template.View;

import org.apollo.template.Controller.*;

/**
 * Enum representing different views in the application.
 * Each enum value corresponds to an FXML file name and its associated controller.
 */
public enum ViewList {

    /**
     * Home view.
     */
    HOME("HomeView.fxml"),

    /**
     * Menu view.
     */
    MENU("MenuView.fxml");

    private final String FXML_FILE_NAME; // FXML file name of the view

    /**
     * Constructs a ViewList enum value with the specified FXML file name and controller.
     *
     * @param fxmlFileName The name of the FXML file representing the view
     * @param controller   The controller associated with the view
     */
    ViewList(String fxmlFileName) {
        this.FXML_FILE_NAME = fxmlFileName;

    }

    /**
     * Returns the FXML file name of the view.
     *
     * @return The FXML file name
     */
    public String getFxmlFileName() {
        return this.FXML_FILE_NAME;
    }

    /**
     * Common loader location for all FXML files in the application.
     */
    public static final String LOADER_LOCATION = "/org/apollo/template/";

}
