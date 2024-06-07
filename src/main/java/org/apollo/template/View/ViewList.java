package org.apollo.template.View;

import org.apollo.template.Controller.*;

/**
 * Enum representing different views in the application.
 * Each enum value corresponds to an FXML file name and its associated controller.
 */
public enum ViewList {

    HOME("HomeView.fxml"),

    MENU("MenuView.fxml"),

    BOOKINGINFO("BookingInformationView.fxml"),

    DELETEBOOKING("DeleteBookingView.fxml"),

    AVAILABLEROOMS("AvailableRoomsView.fxml"),

    SYSTEMCHOSE("SystemChoseView.fxml"),

    BOOKINGCOMPLITE("BookingViewComplete.fxml"),

    ADMINMENU("AdminMenuBar.fxml"),

    VIEWERRORREPORT("ViewErrorReportView.fxml"),

    ERRORREPORTEDIT("ErrorReportEditView.fxml"),

    CREATEERRORREPORT("CreateErrorReportView.fxml"),

    INFOSCREEN("InfoView.fxml"),

    CHOOSETIME("ChooseTimeView.fxml"),

    ALLROOMS("AllRoomsView.fxml"),

    ADMINDELETEBOOKING("AdminDeleteBookingView.fxml"),

    ADMINVIEWBOOKINGS("AdminViewBookings.fxml"),

    EDITROOM("EditRoomView.fxml"),

    CREATEROOM("CreateRoomView.fxml"),

    TOPBAR("TopbarView.fxml"),

    STATISTICS("StatisticsView.fxml"),

    INVENTORYITEMS("InventoryItemsView.fxml"),

    CREATEINVENTORYITEMS("CreateInventoryItemView.fxml"),

    EDITINVENTORYITEMS("EditInventoryItemView.fxml"),
    /**
     * Team view
     */
    TEAMVIEW("teamView.fxml"),
    /**
     * Create team view
     */
    CREATETEAMVIEW("CreateTeam.fxml"),

    LOGIN("AdminLoginView.fxml"),

    ADMINCREATEBOOKING("AdminCreateBooking.fxml");

    private final String FXML_FILE_NAME; // FXML file name of the view

    /**
     * Constructs a ViewList enum value with the specified FXML file name and controller.
     *
     * @param fxmlFileName The name of the FXML file representing the view
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
