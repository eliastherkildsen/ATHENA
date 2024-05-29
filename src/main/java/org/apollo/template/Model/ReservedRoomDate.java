package org.apollo.template.Model;


public class ReservedRoomDate {
    /**
     * tbl_booking.fld_startTime,
     *         tbl_booking.fld_endTime,
     *         tbl_booking.fld_userName,
     *         tbl_room.fld_roomName
     */
    private String startTime;
    private String endTime;
    private String userName;
    private String roomName;
    private String meetingType;

    public ReservedRoomDate(String startTime, String endTime, String userName, String roomName, String meetingType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userName = userName;
        this.roomName = roomName;
        this.meetingType = meetingType;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getMeetingType() {
        return meetingType;
    }
}
