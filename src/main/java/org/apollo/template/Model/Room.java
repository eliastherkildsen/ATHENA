package org.apollo.template.Model;

public class Room {
    private int roomID;
    private int roomMaxPersonCount;
    private int roomTypeID;
    private int floor;

    private String roomName;

    private RoomType roomType;

    public Room() {
    }

    public Room(int roomId) {
        this.roomID = roomId;
    }



    public Room(int roomId, int roomMaxPersonCount, int roomTypeID, int floor, String roomName) {
        this.roomID = roomId;
        this.roomMaxPersonCount = roomMaxPersonCount;
        this.roomTypeID = roomTypeID;
        this.floor = floor;
        this.roomName = roomName;
    }

    public Room(int roomID, int roomMaxPersonCount, int roomTypeID, int floor, String roomName, RoomType roomType) {
        this.roomID = roomID;
        this.roomMaxPersonCount = roomMaxPersonCount;
        this.roomTypeID = roomTypeID;
        this.floor = floor;
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomId) {
        this.roomID = roomId;
    }

    public int getRoomMaxPersonCount() {
        return roomMaxPersonCount;
    }

    public void setRoomMaxPersonCount(int roomMaxPersonCount) {
        this.roomMaxPersonCount = roomMaxPersonCount;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    @Override
    public String toString() {
        return "sal. " + floor + " lok " + roomName;
    }
}