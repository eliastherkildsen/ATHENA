package org.apollo.template.Model;


/*
    Object representation of meeting type.
 */
public class MeetingType {

    private String meetingType;
    private int meetingTypeID;

    public MeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public MeetingType(int meetingTypeID) {
        this.meetingTypeID = meetingTypeID;
    }

    public MeetingType(String meetingType, int meetingTypeID) {
        this.meetingType = meetingType;
        this.meetingTypeID = meetingTypeID;
    }

    public int getMeetingTypeID() {
        return meetingTypeID;
    }

    public String getMeetingTypeName() {
        return meetingType;
    }

    public void setMeetingTypeID(int meetingTypeID) {
        this.meetingTypeID = meetingTypeID;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    @Override
    public String toString() {
        return meetingType;
    }
}