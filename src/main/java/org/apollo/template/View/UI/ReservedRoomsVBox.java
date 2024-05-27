package org.apollo.template.View.UI;

import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ReservedRoomsVBox extends VBox {

    List rooms = new ArrayList();

    ReservedRoomsVBox(List Reservedrooms) {
        rooms.addAll(Reservedrooms);
    }

    private void addAllRoomsToVbox(){

    }


}
