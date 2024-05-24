package org.apollo.template.View.UI;

import javafx.scene.control.TextArea;
import org.apollo.template.Service.Alert.AlertType;

public class AlertComp extends TextArea {

    private final int MIN_HEIGHT = 100;
    private final int MIN_WIDTH = 300;
    private final int MAX_WIDTH = 300;
    private final int LINE_SIZE_IN_PX = 21; // the amount the text area grows with for each "\n" in the message.

    public AlertComp(AlertType alertType, String message) {

        // setting text of the Text area.
        super(message);

        int totalNumberOfLines = getLineCount();

        setMouseTransparent(true); // Enable ensures that the client can not interact with the alert.
        setWrapText(true);         // Enable text wrapping
        applyStyle(alertType);     // setting css props.

        // setting max width to keep the alert component stay consistent on its width property.
        setMaxWidth(MAX_WIDTH);
        setMinWidth(MIN_WIDTH);


        // setting height constraints.
        setMaxHeight(LINE_SIZE_IN_PX * totalNumberOfLines); // Allow the TextArea to shrink if needed
        setMinHeight(MIN_HEIGHT);

    }

    /**
     * Get the number of lines in the TextArea.
     * @return The number of lines.
     */
    public int getLineCount() {
        String text = getText();
        if (text == null || text.isEmpty()) {
            return 0;
        }
        return text.split("\n", -1).length;
    }

    /**
     * Method for loading the right style on the AlertComponent.
     * @param alertType associated value for determining the style to be used.
     */
    private void applyStyle(AlertType alertType) {
        switch (alertType) {
            case ERROR:
                getStyleClass().add("alertError");
                break;
            case SUCCESS:
                getStyleClass().add("alertSuccess");
                break;
            case INFO:
                getStyleClass().add("alertInfo");
                break;
        }

        // Load the custom style sheet for the alertComponent.
        getStylesheets().add(getClass().getResource("/org/apollo/template/css/alertStyles.css").toExternalForm());
    }
}
