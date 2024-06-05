package org.apollo.template.View.UI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apollo.template.Model.ErrorReport;
import java.util.ArrayList;
import java.util.List;


public class ErrorReportComp extends DefualtComponent {

    private final ErrorReport ERROR_REPORT;

    public ErrorReportComp(ErrorReport errorReport) {
        this.ERROR_REPORT = errorReport;
        loadErrorReport();
    }

    /**
     * Method for loading all labels, and adding to this(Vbox)
     */
    private void loadErrorReport() {
        // fetching data and loading into labels.
        Label lable_reportDate = new Label(ERROR_REPORT.getReportDate().toString());
        Label lable_item = new Label(ERROR_REPORT.getInventoryItems().getName());
        Label label_email = new Label(ERROR_REPORT.getEmail().getEmail());
        Label label_room = new Label(ERROR_REPORT.getRoom().getRoomName());

        // adding labels to a list to iterate thrugh them when styling.
        List<Label> labels = new ArrayList<Label>();
        labels.add(lable_reportDate);
        labels.add(lable_item);
        labels.add(label_room);
        labels.add(label_email);

        for (Label label : labels) {
            styleLable(label);
        }

        // adding labels to the hbox.
        this.getChildren().addAll(label_room, lable_reportDate, lable_item, label_email);
    }


    // region getter & setters
    public CompColors getCompColors() {
        return super.getCompColor();
    }

    public void setCompColors(CompColors compColors) {
        super.setCompColor(compColors);
    }

    public ErrorReport getERROR_REPORT() {
        return ERROR_REPORT;
    }

    // endregion


}
