package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
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
        for(Team t : teams) {
            for(Player p : t.getMembers()) {
                if (player.equals(p)) {
                    player.sendMessage("You can't join this team, you are already on a team!");
                    return;
                }
            }
        }

        teamToJoin.joinTeam(player);
        player.sendMessage("You successfully joined the team!");
    }

    public Team getTeamFromName(String name) {
        for(Team t : teams) {
            if (t.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return t;
            }
        }

        return null;
    }

    public void getAvailableTeams() {
        
    }

    public Team playerOnTeam(Player player) {
        for(Team t : teams) {
            for(Player p : t.getMembers()) {
                if (p.getDisplayName().equals(player.getDisplayName())) {
                    return t;
                }
            }
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }
}
