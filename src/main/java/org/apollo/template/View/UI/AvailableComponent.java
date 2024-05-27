package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AvailableComponent extends HBox {



    public AvailableComponent(String roomNo, String floor, int personKapacity, String inventory){

        Label label_roomNo = buildLabel(roomNo, 18, FontWeight.BOLD);
        Label label_floor = buildLabel(floor, 18, FontWeight.NORMAL);
        Label label_personKap = buildLabelWithInt(personKapacity, 18, FontWeight.NORMAL);

    }

    private Label buildLabel(String text, int fontSize, FontWeight fontWeight) {
        Label label = new Label(text);
        label.setFont(Font.font("System", fontWeight, FontPosture.REGULAR, fontSize));
        this.setHgrow(label, Priority.ALWAYS);
        this.setMargin(label, new Insets(0,20,0,20));
        return label;
    }

    private Label buildLabelWithInt(int number, int fontSize, FontWeight fontWeight) {

        String kapacity = String.valueOf(number);

        Label label = new Label(kapacity);
        label.setFont(Font.font("System", fontWeight, FontPosture.REGULAR, fontSize));
        this.setHgrow(label, Priority.ALWAYS);
        this.setMargin(label, new Insets(0,20,0,20));
        return label;
    }




    private void forNow(){
        Label label_availableRoom = new Label();
        label_availableRoom.setMaxHeight(77.5);
        label_availableRoom.setMaxWidth(600);

        getChildren().addAll(label_availableRoom);



        VBox vbox = new VBox();



        HBox hBox_roomInfo = new HBox();
        hBox_roomInfo.setAlignment(Pos.CENTER_LEFT);

        HBox hBox_inventoryInfo = new HBox();
        hBox_inventoryInfo.setAlignment(Pos.CENTER_LEFT);

        vbox.getChildren().addAll(hBox_roomInfo,hBox_inventoryInfo);


        Label label_localNo = new Label();
        Label label_floor = new Label();
        Label label_personKapacity = new Label();

        hBox_roomInfo.getChildren().addAll(label_localNo, label_floor, label_personKapacity);

        Label label_inventory = new Label();
        hBox_inventoryInfo.getChildren().addAll(label_inventory);
    }


}
