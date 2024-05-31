package org.apollo.template.Model;

import java.sql.Time;
import java.util.Date;

public class Booking {

    private int bookingID = -1;
    private Time startTime;
    private Time endTime;
    private Date date;
    private String username;
    private Room room;
    private MeetingType meetingType;

    // this is an empty constructor, due to this class being a builder pattern.
    public Booking() {}

    public Booking setBookingID(int bookingID) {
        this.bookingID = bookingID;
        return this;
    }

    public Booking setStartTime(Time startTime) {
        this.startTime = startTime;
        return this;
    }

    public Booking setEndTime(Time endTime) {
        this.endTime = endTime;
        return this;
    }

    public Booking setUsername(String username) {
        this.username = username;
        return this;
    }

    public Booking setRoom(Room room) {
        this.room = room;
        return this;
    }

    public Booking setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
        return this;
    }

    public Booking setDate(Date date) {
        this.date = date;
        return this;
    }

    public int getBookingID() {
        return bookingID;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getUsername() {
        return username;
    }

    public Room getRoom() {
        return room;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", username='" + username + '\'' +
                ", room=" + room +
                ", meetingType=" + meetingType +
                '}';
    }
}
