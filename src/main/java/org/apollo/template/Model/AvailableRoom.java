package org.apollo.template.Model;

public class AvailableRoom {

    private String roomName;
    private String floor;
    private String roomType;
    private int personKapacity;

    public AvailableRoom(String roomName, String floor, String roomType, int personKapacity) {
        this.roomName = roomName;
        this.floor = floor;
        this.roomType = roomType;
        this.personKapacity = personKapacity;
    }

    // region getter and setter

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPersonKapacity() {
        return personKapacity;
    }

    public void setPersonKapacity(int personKapacity) {
        this.personKapacity = personKapacity;
    }

    // endregion

}
