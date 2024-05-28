package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * BookingComp is a compont that builds a HBox with the infromation we want displayed.
 */
public class BookingComp extends HBox {
    /**
     * BookingComp builds an HBox with the information and format we require.
     * @param roomName String
     * @param userName String
     * @param meetingType String
     * @param startAndEndTime String
     */
    public BookingComp(String roomName, String userName, String meetingType, String startAndEndTime){

        Label labelBookingRoom = buildLabel(roomName, 18, FontWeight.BOLD);
        labelBookingRoom.setPrefWidth(100);

        Label labelBookerName = buildLabel(userName, 18, FontWeight.NORMAL);
        labelBookerName.setPrefWidth(200);

        Label labelBookingName = buildLabel(meetingType, 18, FontWeight.NORMAL);
        labelBookingName.setPrefWidth(150);

        Label labelBookingTime = buildLabel(startAndEndTime, 18, FontWeight.NORMAL);

        this.setMinHeight(35);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setStyle("-fx-background-color: #009FE3; -fx-background-radius: 40");
        this.getChildren().addAll(labelBookingRoom, labelBookingName, labelBookerName, addPane(), labelBookingTime);
    }

    /**
     * Helper Method for BookingComp
     * Generates label in the style we want.
     * @param text String : label text
     * @param fontSize Int
     * @param style FrontWeigth
     * @return Label
     */
    private Label buildLabel(String text, int fontSize, FontWeight style) {
        Label label = new Label(text);
        label.setFont(Font.font("System", style, FontPosture.REGULAR, fontSize));
        this.setHgrow(label, Priority.ALWAYS);
        this.setMargin(label, new Insets(0,20,0,20));
        return label;
    }

    /**
     * Adds Pane in style we want.
     * @return Pane
     */
    private Pane addPane (){
        Pane pane = new Pane();
        this.setHgrow(pane, Priority.ALWAYS);
        return pane;
    }

}
