package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apollo.template.Service.Logger.LoggerMessage;

public abstract class DefualtComponent extends HBox {

    private CompColors compColors;
    private final int FONT_SIZE = 18;
    private CompColors color = CompColors.NORMAL;

    public DefualtComponent() {
        styleHbox(this);
    }

    /**
     * Method for styling a label
     * @param label Label
     */
    public void styleLable(Label label){
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE));
        LoggerMessage.debug(this, "styled lable: " + label);
    }

    /**
     * Method for stylinge a Hbox
     * @param hbox Hbox
     */
    public void styleHbox(HBox hbox){
        hbox.setSpacing(50);
        hbox.setStyle("-fx-background-color: " + color.getColor() + "; -fx-background-radius: 40;");
        hbox.setMinHeight(60);
        hbox.setPadding(new Insets(0, 20, 0, 0));
        hbox.setAlignment(Pos.CENTER);

        LoggerMessage.debug(this, "Styled hbox: " + hbox);

    }

    protected CompColors getCompColor() {
        return compColors;
    }

    protected void setCompColor(CompColors compColors) {
        this.compColors = compColors;
        this.setStyle("-fx-background-color: " + compColors.getColor() + "; -fx-background-radius: 40;");
    }


    // endregion

}
