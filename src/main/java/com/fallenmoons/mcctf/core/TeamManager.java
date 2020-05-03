package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TeamManager {

    private Main main;
    private ArrayList<Team> teams;

    public TeamManager(Main main) {
        this.main = main;
        teams = new ArrayList<Team>();
    }

    public void joinTeam(Team teamToJoin, Player player) {
        if (teams.size() > 0) {
            for(Team t : teams) {
                for(Player p : t.getMembers()) {
                    if (player.equals(p)) {
                        player.sendMessage(ChatColor.RED + "You can't join this team, you are already on a team!");
                        return;
                    }
                }
            }

            teamToJoin.joinTeam(player);
            player.sendMessage(ChatColor.GREEN + "You successfully joined the team!");
        }
    }

    public void createTeam(String teamName, ChatColor teamColor) {
        Team team = new Team(teamName, teamColor);
        teams.add(team);
    }

    public Team getTeamFromName(String name) {
        for(Team t : teams) {
            if (t.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return t;
            }
        }

        return null;
    }

    public boolean teamExists(String name) {
        for(Team t : teams) {
            if (t.getTeamName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public Team getTeamFromPlayer(Player player) {
        for(Team t : teams) {
            for(Player p : t.getMembers()) {
                if (p.getDisplayName().equals(player.getDisplayName())) {
                    return t;
                }
            }
        }
        return null;
    }

    public boolean playerOnTeam(Player player) {
        for(Team t : teams) {
            for(Player p : t.getMembers()) {
                if (p.getDisplayName().equals(player.getDisplayName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public boolean colorInUse(ChatColor color) {
        for(Team t : teams) {
            if (t.getTeamColor().equals(color)) {
                return true;
            }
        }

        return false;
    }

    public boolean nameInUse(String name) {
        for(Team t : teams) {
            if (t.getTeamName().equals(name)) {
                return true;
            }
        }

        return false;
    }

}
