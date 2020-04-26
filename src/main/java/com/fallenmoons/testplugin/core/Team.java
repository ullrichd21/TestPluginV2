package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Team {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    private String teamName;
    private ArrayList<Player> players;

    public Team(String name) {
        teamName = name;
        players = new ArrayList<Player>();
    }

    public ArrayList<Player> getMembers() {
        return players;
    }

    public int getNumberOfMembers() {
        return players.size();
    }

    public String getTeamName() {
        return teamName;
    }

    public void joinTeam(Player player) {
        players.add(player);
        player.setDisplayName(player.getDisplayName());
        player.setPlayerListName(ChatColor.valueOf(teamName.toUpperCase()) + player.getDisplayName());
    }

}
