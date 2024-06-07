package org.apollo.template.View.UI;

import javafx.scene.control.Label;
import org.apollo.template.Model.Team;

public class TeamComp extends DefualtComponent{

    private final Team TEAM;

    public TeamComp(Team team) {
        this.TEAM = team;
        loadTeamComp();
    }

    private void loadTeamComp() {

        // initializing label
        Label teamName = new Label(TEAM.getTeamName());

        // styling label.
        styleLable(teamName);

        // add label to this (hbox)
        this.getChildren().add(teamName);
    }

    // region getter & setters.


    public Team getTeam() {
        return TEAM;
    }

    // endregion



}
