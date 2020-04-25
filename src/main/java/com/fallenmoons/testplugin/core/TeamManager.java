package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TeamManager {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);
    private ArrayList<Team> teams;

    public TeamManager(String[] team_names) {
        teams = new ArrayList<Team>();
        for(String t : team_names) {
            teams.add(new Team(t));
        }
    }

    public void joinTeam(Team teamToJoin, Player player) {
        teamToJoin.joinTeam(player);
    }

    public Team getTeamFromName(String name) {
        for(Team t : teams) {
            if (t.getTeamName().equals(name)) {
                return t;
            }
        }

        return null;
    }

    public void getAvailableTeams() {
        
    }

}
