package org.apollo.template.Model;


/*
    Object representation of meeting type.
 */
public class MeetingType {

    private final String MEETING_TYPE;
    private int meetingTypeID;

    public MeetingType(String meetingType) {
        this.MEETING_TYPE = meetingType;
    }

    public MeetingType(String meetingType, int meetingTypeID) {
        this.MEETING_TYPE = meetingType;
        this.meetingTypeID = meetingTypeID;
    }

    public int getMeetingTypeID() {
        return meetingTypeID;
    }

    public String getMeetingType() {
        return MEETING_TYPE;
    }

    @Override
    public String toString() {
        return MEETING_TYPE;
    }
}
