package org.apollo.template.Model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class Booking {

    boolean adHoc;
    private int bookingID = -1;
    private Time startTime;
    private Time endTime;
    private LocalDate date;
    private String username;
    private Room room;
    private MeetingType meetingType;
    private int NumberOfParticipants;
    private Email email;

    // this is an empty constructor, due to this class being a builder pattern.
    public Booking() {}

    public Booking setBookingID(int bookingID) {
        this.bookingID = bookingID;
        return this;
    }

    public Booking setEmail(Email email) {
        this.email = email;
        return this;
    }

    public Booking setNumberOfParticipants(int NumberOfParticipants) {
        this.NumberOfParticipants = NumberOfParticipants;
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

    public Booking setDate(LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public int getNumberOfParticipants() {
        return NumberOfParticipants;
    }

    public Email getEmail() {
        return email;
    }

    public boolean isAdHoc() {
        return adHoc;
    }

    public void setAdHoc(boolean adHoc) {
        this.adHoc = adHoc;
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