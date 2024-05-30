package org.apollo.template.Model;

public class AvailableRoom extends Room{

    private int roomID;
    private String roomName;
    private int floor;
    private String roomType;
    private int personKapacity;


    public AvailableRoom(int roomID, String roomName, int floor, String roomType, int personKapacity) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.floor = floor;
        this.roomType = roomType;
        this.personKapacity = personKapacity;
    }


    // region getter and setter

    public int getRoomID() { return roomID; }

    public void setRoomID(int roomID) { this.roomID = roomID; }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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


    /**
     * This method constructs a string containing information about the room, including its name, floor, room type, and capacity
     * @return a string containing information about the room (name, floor, type, and capacity)
     */
    public String toString(){
        return String.format("Lok. %s - %d. Sal - Lok. type: %s - Person kapacitet: %d", roomName, floor, roomType, personKapacity);
    }

}
