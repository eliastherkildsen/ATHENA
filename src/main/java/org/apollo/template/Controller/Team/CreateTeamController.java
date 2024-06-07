package org.apollo.template.Controller.Team;

import javafx.fxml.FXML;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Team;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.TeamDAO;

import java.awt.*;

public class CreateTeamController {

    @FXML
    private javafx.scene.control.TextField textField_teamName;

    @FXML
    protected void onButton_create(){

        // validates that a team name has been given.
        if (textField_teamName.getText().isEmpty()){
            // alert the user to enter at team name.
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke givet " +
                    "holdet et navn.").start();
            return;
        }

        // create new team obj.
        Team team = new Team(textField_teamName.getText());
        // creating dao and creating team in the database.
        DAO<Team> teamDAO = new TeamDAO();
        teamDAO.add(team);

        // alert the user that a new team has been created.
        new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har nu oprettet et hold ")
                .start();
    }

    @FXML
    protected void onButton_back(){
        // sending the user back to team view.
        MainController.getInstance().setView(ViewList.TEAMVIEW, BorderPaneRegion.CENTER);
    }


}
