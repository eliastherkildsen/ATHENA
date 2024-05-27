package org.apollo.template.Service;

import javafx.scene.control.TextField;

public class TextFieldInputValidation {
    /**
     * Method for attaching input validation to a text field, method makes text field only accept
     * integer values.
     * @param textFields textField
     */
    public static void attatchIntegerValidation(TextField... textFields) {

        for (TextField textField : textFields) {

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Filter out non-integer characters
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }
    }
}
