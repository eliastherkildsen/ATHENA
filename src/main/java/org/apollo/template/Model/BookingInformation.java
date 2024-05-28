package org.apollo.template.Model;

import java.sql.Date;

public class BookingInformation {

    private int bookingId;
    private String userName;
    private String startTime;
    private String endTime;
    private Date date;
    private boolean catering;
    private int numberOfParticipants;
    private Email email;
    private int roomId;
    private MeetingType meetingType;
    private int teamId;
    private int departmentID;

    public BookingInformation() {

    }

    /**
     * @param startTime String
     * @param endTime String
     * @param date DateSQL
     * @param catering int converts to boolean
     * @param numberOfParticipants int
     * @param userName String
     * @param roomId int
     * @param departmentID int
     * @param teamId int
     */
    public BookingInformation (
            String startTime,
            String endTime,
            Date date,
            int catering,
            int numberOfParticipants,
            String userName,
            int roomId,
            int departmentID,
            int teamId
    ){
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setDate(date);
        this.setCateringBitToBoolean(catering);
        this.setNumberOfParticipants(numberOfParticipants);
        this.setUserName(userName);
        this.setRoomId(roomId);
        this.setDepartmentID(departmentID);
        this.setTeamId(teamId);
    }

    /**
     *
     * @param startTime String
     * @param endTime String
     * @param date DateSQL
     * @param catering Boolean
     * @param numberOfParticipants int
     * @param userName String
     * @param roomId int
     * @param departmentID int
     * @param teamId int
     */
    public BookingInformation (
            String startTime,
            String endTime,
            Date date,
            Boolean catering,
            int numberOfParticipants,
            String userName,
            int roomId,
            int departmentID,
            int teamId
    ){
        this.setStartTime(startTime);
        this.setEndTime(endTime);
        this.setDate(date);
        this.setCatering(catering);
        this.setNumberOfParticipants(numberOfParticipants);
        this.setUserName(userName);
        this.setRoomId(roomId);
        this.setDepartmentID(departmentID);
        this.setTeamId(teamId);
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
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

    public boolean isCatering() {
        return catering;
    }

    public int isCateringBit(){
        if (!catering)
            return 0;
        else {
            return 1;
        }
    }

    public void setCateringBitToBoolean(int bit){
        if (bit == 0)
            this.catering = false;
        else {
            this.catering = true;
        }
    }

    public void setCatering(boolean catering) {
        this.catering = catering;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }
}