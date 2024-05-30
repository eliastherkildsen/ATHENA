package org.apollo.template.Model;

public class AvailableRoom extends Room{

    private int roomID;
    private String roomName;
    private int floor;
    private int personKapacity;
    private int roomTypeID;
    private String roomType;



    public AvailableRoom(int roomID, String roomName, int floor, String roomType, int personKapacity, int roomTypeID) {
        super(roomID, personKapacity, roomTypeID, floor, roomName);
        this.roomType = roomType;
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

    @Override
    public int getRoomTypeID() { return roomTypeID; }

    @Override
    public void setRoomTypeID(int roomTypeID) { this.roomTypeID = roomTypeID; }

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
