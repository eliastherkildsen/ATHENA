package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AvailableComponent extends HBox {



    public AvailableComponent(String roomNo, String floor, int personKapacity, String inventory){

        // creates labels using the buildLabel methods
        Label label_roomNo = buildLabel("Lok. ", roomNo, 18, FontWeight.BOLD);
        Label label_floor = buildLabel("", floor, 18, FontWeight.BOLD);
        Label label_personKap = buildLabel("Person kapacitet: ", String.valueOf(personKapacity), 18, FontWeight.BOLD);
        Label label_inventory = buildLabel("IT-udstyr og inventar: ", inventory, 16, FontWeight.NORMAL);

        // creates button to book the room
        Button button_book = new Button("Book");
        button_book.setPrefHeight(33.6);
        button_book.setPrefWidth(60);


        // creates hBox to hold room info + button "book"
        HBox hbox_roomInfo = new HBox(label_roomNo,label_floor,label_personKap, button_book);
        hbox_roomInfo.setAlignment(Pos.CENTER_LEFT);
        hbox_roomInfo.setPadding(new Insets(10,10,5,10));
        hbox_roomInfo.setSpacing(50);

        HBox.setMargin(button_book, new Insets(0,0,0,450));


        // creates hBox to hold inventory info
        HBox hbox_inventory = new HBox(label_inventory);
        hbox_inventory.setAlignment(Pos.CENTER_LEFT);
        hbox_inventory.setPadding(new Insets(0,10,10,10));      // første = top , anden = højre, tredje = ned, fjerde = venstre
        hbox_inventory.setSpacing(10);

        // creates vBox to hold the hBoxes
        VBox vbox = new VBox(hbox_roomInfo,hbox_inventory);
        vbox.setPrefWidth(1200);

        
        // sets up the main hBox
        this.setPrefWidth(600);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #f4f40a; -fx-background-radius: 40");
        this.getChildren().add(vbox);
    }


    private Label buildLabel(String text, String information, int fontSize, FontWeight fontWeight) {
        // SPAS
        String checkMark = "\u2713";



        Label label = new Label(text + information + checkMark);
        label.setFont(Font.font("System", fontWeight, FontPosture.REGULAR, fontSize));
        this.setHgrow(label, Priority.ALWAYS);
        this.setMargin(label, new Insets(0, 20, 0, 20));
        return label;
    }


}
