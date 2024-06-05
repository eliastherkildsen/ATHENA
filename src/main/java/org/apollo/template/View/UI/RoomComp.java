package org.apollo.template.View.UI;

import javafx.scene.control.Label;
import org.apollo.template.Model.Room;


public class RoomComp extends DefualtComponent{

    private Room room;

    public RoomComp(Room room) {
        this.room = room;
        loadRoom();
    }

    private void loadRoom(){
        // loading room data into labels.
        Label roomName = new Label(room.getRoomName());
        Label roomFloor = new Label(String.valueOf(room.getFloor()));

        // stylinge labels
        styleLable(roomName);
        styleLable(roomFloor);

        // adding labels to hbox.
        this.getChildren().addAll(roomName, roomFloor);
    }

    // region getter & setter
    public Room getRoom() {
        return room;
    }

    public CompColors getCompColors() {
        return super.getCompColor();
    }

    public void setCompColors(CompColors compColors) {
        super.setCompColor(compColors);
    }

    // endregion
}
