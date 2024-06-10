package org.apollo.template.persistence.JDBC.DAO;

import org.apollo.template.Database.JDBC;
import org.apollo.template.Model.Team;
import org.apollo.template.Service.Logger.LoggerMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO extends DAOAbstract<Team> {

    @Override
    public void add(Team team) {
        PreparedStatement ps = null;
        try{
            ps = getCONN().prepareStatement("INSERT INTO tbl_team (fld_teamName) values(?)");
            ps.setString(1, team.getTeamName());
            ps.executeUpdate();
            LoggerMessage.info(this, "In add; added; " + team.getTeamName());

        } catch (SQLException e){
            LoggerMessage.error(this,"In add; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
            }
        }

    @Override
    public void delete(Team team) {
        PreparedStatement ps = null;
        try {
            ps = getCONN().prepareStatement("DELETE FROM tbl_team WHERE fld_teamID = ?");
            ps.setInt(1, team.getTeamId());
            ps.executeUpdate();
            LoggerMessage.info(this, "In delete; deleted; " + team.getTeamName());

        } catch (SQLException e){
            LoggerMessage.error(this,"In delete; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }

    }

    @Override
    public void update(Team team) {
        PreparedStatement ps = null;
        try {
            ps = getCONN().prepareStatement("UPDATE tbl_team SET fld_teamName = ? WHERE fld_teamID = ?");
            ps.setString(1, team.getTeamName());
            ps.setInt(2, team.getTeamId());
            ps.executeUpdate();
            LoggerMessage.info(this,"In update; updated; " + team.getTeamName());

        } catch (SQLException e){
            LoggerMessage.error(this,"In update; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
        }
    }

    @Override
    public Team read(int id) {
        Team team = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_team WHERE fld_teamID = ?");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                team.setTeamId(rs.getInt("fld_teamID"));
                team.setTeamName(rs.getString("fld_teamName"));
            }
            LoggerMessage.debug(this,"In read; read; " + team.getTeamName());

        } catch (SQLException e) {
            LoggerMessage.error(this,"In read; An error occurred " + e.getMessage());
        } finally {
            closeprePareStatement(ps);
            closeResultSet(rs);
        }
        return team;
    }

    @Override
    public List<Team> readAll() {
        List<Team> teams = new ArrayList<Team>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = getCONN().prepareStatement("SELECT * FROM tbl_team");
            rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team((rs.getInt("fld_teamID")), rs.getString("fld_teamName"));
                teams.add(team);
            }
            LoggerMessage.info(this, "In readAll; list; " + teams.size());

        } catch (SQLException e){
            LoggerMessage.error(this,"In readAll; An error occurred " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closeprePareStatement(ps);
        }
        return teams;
    }
}
