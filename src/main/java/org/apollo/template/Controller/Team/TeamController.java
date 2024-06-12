package org.apollo.template.Controller.Team;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apollo.template.Controller.MainController;
import org.apollo.template.Model.Team;
import org.apollo.template.Service.Alert.Alert;
import org.apollo.template.Service.Alert.AlertType;
import org.apollo.template.Service.Logger.LoggerMessage;
import org.apollo.template.View.BorderPaneRegion;
import org.apollo.template.View.UI.CompColors;
import org.apollo.template.View.UI.TeamComp;
import org.apollo.template.View.ViewList;
import org.apollo.template.persistence.JDBC.DAO.DAO;
import org.apollo.template.persistence.JDBC.DAO.TeamDAO;
import org.apollo.template.persistence.PubSub.MessagesBroker;
import org.apollo.template.persistence.PubSub.MessagesBrokerTopic;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TeamController implements Initializable {

    private TeamComp selectedTeam;
    private DAO<Team> teamDAO = new TeamDAO();
    private List<TeamComp> teamList = new ArrayList<>();
    @FXML
    private VBox vbox_teamComponents;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // loading all teams, and adding to vbox.
        createTeamComp();
    }


    /**
     * Method for creating team components from team
     * @return List<TeamComp>
     */
    private void createTeamComp(){

        List<Team> teams = teamDAO.readAll();

        // debugging
        LoggerMessage.debug(this, "fetched teams from db: team count " + teams.size());

        for (Team team : teams){
            TeamComp teamComp = new TeamComp(team);
            // adding on action to the team component.
            teamComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // unselect all team comps.
                    unselectTeamComps();
                    // selecting the clicked component
                    selectedTeam = teamComp;
                    teamComp.setCompColor(CompColors.SELECTED);
                }
            });
            // adding the teamComp to list for later iterations.
            teamList.add(teamComp);
            LoggerMessage.debug(this, "added new team to " + teamList);

        }

        // adding teamComponents to vbox
        vbox_teamComponents.getChildren().addAll(teamList);


    }

    /**
     * Method for setting all team comps colors to normal
     */
    private void unselectTeamComps() {
        for (TeamComp teamComp : teamList){
            teamComp.setCompColor(CompColors.NORMAL);
        }
    }

    // region buttons

    @FXML
    protected void onButton_delete(){
        // validate that an inventory item has been selected.
        if (!checkIfSelected()) return;

        // check if the item appears as foreign key
        try {
            teamDAO.delete(selectedTeam.getTeam());
        } catch (RuntimeException e) {
            // alert user that the team can not be deleted due to it being used in a booking.
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du kan ikke slette" +
                    " dette hold da det er en del af en booking.").start();
            return;
        }

        // alert the user that it went well,
        new Alert(MainController.getInstance(), 5, AlertType.SUCCESS, "Du har nu slette holdet!")
                .start();

        vbox_teamComponents.getChildren().remove(selectedTeam);

    }


    @FXML
    protected void onButton_add(){
        // send user to view for creating an item
        MainController.getInstance().setView(ViewList.CREATETEAMVIEW, BorderPaneRegion.CENTER);
    }

    /**
     * Method for validating that an item has been selected. Starts alert.
     * @return Boolean
     */
    private boolean checkIfSelected(){
        if(selectedTeam == null){
            // alert user to select an item.
            new Alert(MainController.getInstance(), 5, AlertType.INFO, "Du har ikke valgt en emne!")
                    .start();
            return false;
        }

        return true;

    }
    // endregion

}
