package org.apollo.template.Model;

import java.sql.Date;
import java.sql.SQLData;

public class BookingInformation {

    private String bookingName;
    private String userName;
    private String startTime;
    private String endTime;
    private Date date;

    public BookingInformation(String bookingName, String userName, String startTime, String endTime, Date date) {
        this.bookingName = bookingName;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
