package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import com.fallenmoons.testplugin.core.Team;
import com.fallenmoons.testplugin.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ViewTeams implements CommandExecutor {
    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);
    private TeamManager teamManager;

    public ViewTeams(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ArrayList<Team> teams = teamManager.getTeams();

            for(Team t : teams) {
                for (Player p : t.getMembers()) {
                    ChatColor color = ChatColor.BLUE;

                    switch(t.getTeamName()) {
                        case "Blue": {color = ChatColor.BLUE; break;}
                        case "Red": {color = ChatColor.RED; break;}
                        case "Green": {color = ChatColor.GREEN; break;}
                    }
                    player.sendMessage(color + t.getTeamName() + ": " + p.getDisplayName());
                }

            }

        } else {
            ArrayList<Team> teams = teamManager.getTeams();

            for(Team t : teams) {
                for (Player p : t.getMembers()) {
                    ChatColor color = ChatColor.BLUE;

                    switch(t.getTeamName()) {
                        case "Blue": {color = ChatColor.BLUE; break;}
                        case "Red": {color = ChatColor.RED; break;}
                        case "Green": {color = ChatColor.GREEN; break;}
                    }
                    plugin.getLogger().info(color + t.getTeamName() + ": " + p.getDisplayName());
                }

            }
        }

        return true;
    }
}
