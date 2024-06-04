package org.apollo.template.View.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import org.apollo.template.Model.ErrorReport;
import org.apollo.template.Model.Room;
import org.apollo.template.Model.RoomType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportComp extends HBox {

    private final ErrorReport ERROR_REPORT;
    private Label lable_room, lable_reportDate, lable_item, label_email, label_room;
    private BookingCompColors color = BookingCompColors.NORMAL;
    private int fontSize = 18;
    private List<Label> labels;



    public ErrorReportComp(ErrorReport errorReport) {
        this.ERROR_REPORT = errorReport;
        labels = new ArrayList<Label>();
        loadErrorReport();

    }

    private void loadErrorReport() {

        lable_reportDate = new Label(ERROR_REPORT.getReportDate().toString());

        lable_item = new Label(ERROR_REPORT.getInventoryItems().getName());

        label_email = new Label(ERROR_REPORT.getEmail().getEmail());

        label_room = new Label(ERROR_REPORT.getRoom().getRoomName());


        // adding labels to a list to iterate thrugh them when styling.
        labels.add(lable_reportDate);
        labels.add(lable_item);
        labels.add(label_room);
        labels.add(label_email);

        for (Label label : labels) {
            styleLable(label);
        }


        // adding labels to the hbox.
        this.getChildren().addAll(label_room, lable_reportDate, lable_item, label_email);
        styleHbox(this);

    }

    private void styleLable(Label label){
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, fontSize));
        LoggerMessage.debug(this, "styled lable: " + label);
    }

    private void styleHbox(HBox hbox){
        hbox.setSpacing(50);
        hbox.setStyle("-fx-background-color: " + color.getColor() + "; -fx-background-radius: 40;");
        hbox.setMinHeight(60);
        hbox.setPadding(new Insets(0, 20, 0, 0));
        hbox.setAlignment(Pos.CENTER);

        LoggerMessage.debug(this, "Styled hbox: " + hbox);

    }

    public void setBookingCompColor(BookingCompColors color) {
        this.color = color;
        this.setStyle("-fx-background-color: "+ this.color.getColor() + "; -fx-background-radius: 40");
    }

    public ErrorReport getErrorReport() {
        return ERROR_REPORT;
    }
}
