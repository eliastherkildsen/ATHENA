package org.apollo.template.Model;


/*
    Object representation of meeting type.
 */
public class MeetingType {

    private String meetingTypeName;
    private int meetingTypeID;

    public MeetingType(){}

    public MeetingType(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    public MeetingType(int meetingTypeID) {
        this.meetingTypeID = meetingTypeID;
    }

    public MeetingType(String meetingTypeName, int meetingTypeID) {
        this.meetingTypeName = meetingTypeName;
        this.meetingTypeID = meetingTypeID;
    }

    public int getMeetingTypeID() {
        return meetingTypeID;
    }

    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    public void setMeetingTypeID(int meetingTypeID) {
        this.meetingTypeID = meetingTypeID;
    }

    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    @Override
    public String toString() {
        return meetingTypeName;
    }
}