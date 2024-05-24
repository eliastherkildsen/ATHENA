package org.apollo.template;

import javafx.fxml.FXMLLoader;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.ViewList;

import java.io.IOException;

public class ViewLoader {

    /**
     * Java method for loading an anchor pane from a FXML file, and forwarding it as an anchor pane obj,
     * with the corresponding controller. Returns NULL if an IOException is thrown.
     *
     * @param fxmlFileName ViewList
     * @return AnchorPane
     */
    public static javafx.scene.Node loadView(ViewList fxmlFileName) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ViewLoader.class.getResource(fxmlFileName.getFxmlFileName())); // Fetches FXML file

        // Log message.
        DebugMessage.info("ViewLoader", "Loading view: " + fxmlFileName.getFxmlFileName());

        try {
            return loader.load(); // Returns anchor pane.

        } catch (IOException e) {
            DebugMessage.error("ViewLoader","Error loading FXML view: " + fxmlFileName + " " + e.getMessage());
            return null;
        }
    }

}
