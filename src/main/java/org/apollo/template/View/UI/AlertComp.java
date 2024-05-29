package org.apollo.template.View.UI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.apollo.template.Service.Alert.AlertType;

public class AlertComp extends VBox{
    Label label = new Label();

    public AlertComp(AlertType alertType, String message) {
        AlertCompMessage alert = new AlertCompMessage(alertType, message);

        label.setText(String.valueOf(alertType));
        label.setFont(Font.font(15));
        label.setAlignment(Pos.CENTER);

        applyStyle(alertType);

        this.getChildren().addAll(label,alert);
        this.setAlignment(Pos.CENTER);

        //We have to magic number a bit here to ensure that all of the text field gets in.
        this.setMinHeight(alert.getMIN_HEIGHT() + 25);
        this.setMaxHeight(alert.getMax_Height());

        //We have to magic number a bit here to ensure that all of the text field gets in.
        this.setMinWidth(alert.getMIN_WIDTH() + 25);
        this.setMaxWidth(alert.getMAX_WIDTH() + 25);
    }

    private void applyStyle(AlertType alertType) {
        switch (alertType) {
            case ERROR:
                getStyleClass().add("alertError");
                label.getStyleClass().add("alertError");
                break;
            case SUCCESS:
                getStyleClass().add("alertSuccess");
                label.getStyleClass().add("alertSuccess");
                break;
            case INFO:
                getStyleClass().add("alertInfo");
                label.getStyleClass().add("alertInfo");
                break;
        }

        // Load the custom style sheet for the alertComponent.
        getStylesheets().add(getClass().getResource("/org/apollo/template/css/alertStyles.css").toExternalForm());
    }
}
