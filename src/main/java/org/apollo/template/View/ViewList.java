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
    MENU("MenuView.fxml"),

    /**
     * Booing info view.
     */
    BOOKINGINFO("BookingInformationView.fxml"),
    /**
    Delete booking view
     */
    DELETEBOOKING("DeleteBookingView.fxml"),


    /**
     * AvailableRooms view.
     */
    AVAILABLEROOMS("AvailableRoomsView.fxml"),

    /**
     * System choose view.
     */
    SYSTEMCHOSE("SystemChoseView.fxml"),
    /**
     * booking complite view.
     */
    BOOKINGCOMPLITE("BookingViewComplete.fxml"),
    /**
     * admin menu view
     */
    ADMINMENU("AdminMenuBar.fxml"),
    /**
     * Error report view
     */
    VIEWERRORREPORT("ViewErrorReportView.fxml"),
    /**
     * Error report edit view
     */
    ERRORREPORTEDIT("ErrorReportEditView.fxml"),
    /**
     * Error report create view
     */
    CREATEERRORREPORT("CreateErrorReportView.fxml"),

    /**
     * Info view.
     */
    INFOSCREEN("InfoView.fxml"),

    /**
     * Choose Time view
     */
    CHOOSETIME("ChooseTimeView.fxml"),

    /**
     * All Rooms view
     */
    ALLROOMS("AllRoomsView.fxml"),

    ADMINDELETEBOOKING("AdminDeleteBookingView.fxml"),

    ADMINVIEWBOOKINGS("AdminViewBookings.fxml"),

    /**
     * Create Room
     */
    CREATEROOM("CreateRoomView.fxml"),

    /**
     *  Topbar view.
     */
    TOPBAR("TopbarView.fxml"),
    /**
     * Statistics view
     */
    STATISTICS("StatisticsView.fxml"),
    /**
     * Inventory items view
     */
    INVENTORYITEMS("InventoryItemsView.fxml"),
    /**
     * Create inventory items view
     */
    CREATEINVENTORYITEMS("CreateInventoryItemView.fxml"),
    /**
     * Edit inventory item view
     */
    EDITINVENTORYITEMS("EditInventoryItemView.fxml"),

    /**
     * Admin login view
     */
    LOGIN("AdminLoginView.fxml");

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
