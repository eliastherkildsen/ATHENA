package org.apollo.template.Model;

import java.time.LocalDate;

public class ErrorReport {

    private Room room;
    private Email email;
    private InventoryItems inventoryItems;
    private String errorReportDescription;
    private boolean archived;
    private LocalDate reportDate;

    public ErrorReport(Room room, Email email, InventoryItems inventoryItems, String errorReportDescription, boolean archived, LocalDate reportDate) {
        this.room = room;
        this.email = email;
        this.inventoryItems = inventoryItems;
        this.errorReportDescription = errorReportDescription;
        this.archived = archived;
        this.reportDate = reportDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public InventoryItems getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(InventoryItems inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public String getErrorReportDescription() {
        return errorReportDescription;
    }

    public void setErrorReportDescription(String errorReportDescription) {
        this.errorReportDescription = errorReportDescription;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
