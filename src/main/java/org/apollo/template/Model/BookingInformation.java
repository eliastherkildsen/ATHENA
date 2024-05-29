package org.apollo.template.Model;

import java.sql.Date;

public class BookingInformation {

    private int bookingId;
    private boolean catering;
    private int numberOfParticipants;
    private int roomId;
    private int teamId;
    private int departmentID;

    private String userName;
    private String startTime;
    private String endTime;
    private Date date;

    private Email email;
    private MeetingType meetingType;
    private Room room;
    private Team team;

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
    }

    public BookingInformation(int bookingId,
                              int catering,
                              int numberOfParticipants,
                              int roomId,
                              int teamId,
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
}