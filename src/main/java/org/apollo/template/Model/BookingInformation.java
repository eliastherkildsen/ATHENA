package org.apollo.template.Model;

import java.sql.Date;

public class BookingInformation {

    private int bookingId;
    private boolean catering;
    private int numberOfParticipants;
    private int roomId;
    private int teamId;
    private int userID;
    private int meetingTypeID;
    private int departmentID;

    private String userName;
    private String startTime;
    private String endTime;
    private Date date;

    private Email email;
    private MeetingType meetingType;
    private Room room;
    private Team team;

    private Boolean adhocBool;

    public BookingInformation() {

    }

    /**
     *
     * @param bookingId
     * @param catering
     * @param numberOfParticipants
     * @param roomId
     * @param teamId
     * @param departmentID
     * @param userName
     * @param startTime
     * @param endTime
     * @param date
     * @param email
     * @param meetingType
     * @param room
     * @param team
     */
    public BookingInformation(int bookingId,
                              boolean catering,
                              int numberOfParticipants,
                              int roomId,
                              int teamId,
                              int userID,
                              int meetingTypeID,
                              int departmentID,
                              String userName,
                              String startTime,
                              String endTime,
                              Date date,
                              Email email,
                              MeetingType meetingType,
                              Room room,
                              Team team)
    {
        this.bookingId = bookingId;
        this.catering = catering;
        this.numberOfParticipants = numberOfParticipants;
        this.roomId = roomId;
        this.teamId = teamId;
        this.departmentID = departmentID;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.email = email;
        this.meetingType = meetingType;
        this.room = room;
        this.team = team;
        this.userID = userID;
        this.meetingTypeID = meetingTypeID;
    }

    /**
     *
     * @param bookingId
     * @param catering
     * @param numberOfParticipants
     * @param roomId
     * @param teamId
     * @param departmentID
     * @param userName
     * @param startTime
     * @param endTime
     * @param date
     * @param email
     * @param meetingType
     * @param room
     * @param team
     */
    public BookingInformation(int bookingId,
                              int catering,
                              int numberOfParticipants,
                              int roomId,
                              int teamId,
                              int userID,
                              int meetingTypeID,
                              int departmentID,
                              String userName,
                              String startTime,
                              String endTime,
                              Date date,
                              Email email,
                              MeetingType meetingType,
                              Room room,
                              Team team)
    {
        this.bookingId = bookingId;
        setCateringBitToBoolean(catering);
        this.numberOfParticipants = numberOfParticipants;
        this.roomId = roomId;
        this.teamId = teamId;
        this.departmentID = departmentID;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.email = email;
        this.meetingType = meetingType;
        this.room = room;
        this.team = team;
        this.userID = userID;
        this.meetingTypeID = meetingTypeID;
    }

    public BookingInformation(int bookingId,
                              int catering,
                              int numberOfParticipants,
                              int roomId,
                              int teamId,
                              int userID,
                              int meetingTypeID,
                              int departmentID,
                              String userName,
                              String startTime,
                              String endTime,
                              Date date)
    {
        this.bookingId = bookingId;
        setCateringBitToBoolean(catering);
        this.numberOfParticipants = numberOfParticipants;
        this.roomId = roomId;
        this.teamId = teamId;
        this.departmentID = departmentID;
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.meetingTypeID = meetingTypeID;
        this.userID = userID;

        this.email = null;
        this.room = null;
        this.team = null;
        this.meetingType = null;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMeetingTypeID() {
        return meetingTypeID;
    }

    public void setMeetingTypeID(int meetingTypeID) {
        this.meetingTypeID = meetingTypeID;
    }

    public Boolean getAdhocBool() {
        return adhocBool;
    }

    public void setAdhocBool(Boolean adhocBool) {
        this.adhocBool = adhocBool;
    }

    @Override
    public String toString() {
        return "BookingInformation{" +
                "bookingId=" + bookingId +
                ", catering=" + catering +
                ", numberOfParticipants=" + numberOfParticipants +
                ", roomId=" + roomId +
                ", teamId=" + teamId +
                ", userID=" + userID +
                ", meetingTypeID=" + meetingTypeID +
                ", departmentID=" + departmentID +
                ", userName='" + userName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", date=" + date +
                ", email=" + email +
                ", meetingType=" + meetingType +
                ", room=" + room +
                ", team=" + team +
                ", adhoc=" + adhocBool +
                '}';
    }
}