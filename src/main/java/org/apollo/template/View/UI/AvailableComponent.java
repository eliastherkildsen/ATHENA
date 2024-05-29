package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apollo.template.Model.AvailableRoom;


public class AvailableComponent extends HBox {

    private AvailableRoom availableRoom;
    private Button button;

    public AvailableComponent(AvailableRoom availableRoom){

        this.availableRoom = availableRoom;

        // creates labels using the buildLabel methods
        Label label_roomNo = buildLabel(String.format("Lok. %s", availableRoom.getRoomName()),18, FontWeight.BOLD);
        Label label_floor = buildLabel(String.format("%s. Sal", availableRoom.getFloor()),18, FontWeight.NORMAL);
        Label label_personKap = buildLabel(String.format("Person kapacitet: %d", availableRoom.getPersonKapacity()), 18, FontWeight.NORMAL);
        Label label_roomType = buildLabel(String.format("Type: %s", availableRoom.getRoomType()), 16, FontWeight.NORMAL);

        // creates button to book the room
        button = createButton("BOOK");


        // sets up the main hBox
        this.setMinHeight(35);
        this.setPadding(new Insets(0, 20, 0, 0));
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #009FE3; -fx-background-radius: 40");
        this.getChildren().addAll(label_roomNo, addPane(),label_floor, addPane(), label_roomType, addPane(), label_personKap, addPane(),button);
    }


    private Label buildLabel(String text, int fontSize, FontWeight fontWeight) {

        Label label = new Label(text);
        label.setFont(Font.font("System", fontWeight, FontPosture.REGULAR, fontSize));
        this.setHgrow(label, Priority.ALWAYS);
        this.setMargin(label, new Insets(20, 20, 20, 20));
        return label;
    }

    private Button createButton(String buttonText){
        Button button = new Button(buttonText);
        button.setPrefHeight(33.6);
        button.setPrefWidth(60);
        button.setStyle("-fx-background-color: #FBBB2C;");
        return button;
    }

    private Pane addPane (){
        Pane pane = new Pane();
        this.setHgrow(pane, Priority.ALWAYS);
        return pane;
    }

    public Button getButton() {
        return button;
    }
}
