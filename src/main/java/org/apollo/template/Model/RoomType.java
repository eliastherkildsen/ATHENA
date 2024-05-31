package org.apollo.template.Model;

public class RoomType {
    private int roomTypeID;
    private String roomTypeName;
    private String roomTypeDescription;

    public RoomType(int roomTypeID, String roomTypeName, String roomTypeDescription) {
        this.roomTypeID = roomTypeID;
        this.roomTypeName = roomTypeName;
        this.roomTypeDescription = roomTypeDescription;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeDescription() {
        return roomTypeDescription;
    }

    public void setRoomTypeDescription(String roomTypeDescription) {
        this.roomTypeDescription = roomTypeDescription;
    }

}

