package org.apollo.template.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.apollo.template.Service.Debugger.DebugMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;

public class MenuController {


    @FXML
    private AnchorPane root;

    @FXML
    protected void onBtnHome() {
        MainController.getInstance().setView(ViewList.HOME, BorderPaneRegion.CENTER);
    }


}
