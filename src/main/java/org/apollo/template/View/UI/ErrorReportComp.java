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
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.RoomDAO;

import java.util.ArrayList;
import java.util.List;

public class ErrorReportComp extends HBox {

    private final ErrorReport ERROR_REPORT;
    private Label lable_room, lable_reportDate, lable_item, label_email;
    private BookingCompColors color = BookingCompColors.NORMAL;
    private int fontSize = 18;
    private List<Label> labels;


    public ErrorReportComp(ErrorReport errorReport) {
        System.out.println("ErrorReportComp");
        this.ERROR_REPORT = errorReport;

        labels = new ArrayList<Label>();

        loadErrorReport();

        System.out.println("loaded ErrorReport");
    }

    private void loadErrorReport() {

        System.out.println("roomid: " + ERROR_REPORT.getRoom().getRoomID());

        DAO<Room> roomDAO = new RoomDAO();
        Room room = roomDAO.read(ERROR_REPORT.getRoom().getRoomID());

        System.out.println("room name: " + ERROR_REPORT.getRoom().getRoomName());

        lable_room = new Label(room.getRoomName());
        lable_reportDate = new Label("date");
        lable_item = new Label("item");
        label_email = new Label("email");

        labels.add(lable_room);
        labels.add(lable_reportDate);
        labels.add(lable_item);
        labels.add(label_email);

        for (Label label : labels) {
            styleLable(label);
        }


        // adding labels to the hbox.
        this.getChildren().addAll(lable_room, lable_reportDate, lable_item, label_email);
        styleHbox(this);

    }

    private void styleLable(Label label){
        label.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, fontSize));
    }

    private void styleHbox(HBox hbox){
        hbox.setSpacing(20);
        hbox.setStyle("-fx-background-color: " + color.getColor() + ";");
        hbox.setStyle("-fx-background-radius: 40");
        hbox.setMinHeight(60);
        hbox.setPadding(new Insets(0, 20, 0, 0));
        hbox.setAlignment(Pos.CENTER);
    }
}
