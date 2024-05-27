package org.apollo.template.model;


/*
    Object representation of meeting type.
 */
public class MeetingType {

    private final String meetingType;

    public MeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getMeetingType() {
        return meetingType;
    }
}
