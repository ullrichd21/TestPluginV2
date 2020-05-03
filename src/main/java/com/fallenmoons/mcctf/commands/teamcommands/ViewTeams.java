package com.fallenmoons.mcctf.commands.teamcommands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.Team;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class ViewTeams implements CommandExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);
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
                    ChatColor color = t.getTeamColor();
                    player.sendMessage(color + t.getTeamName() + ": " + p.getDisplayName());
                }

            }

        } else {
            ArrayList<Team> teams = teamManager.getTeams();

            for(Team t : teams) {
                for (Player p : t.getMembers()) {
                    plugin.getLogger().info(t.getTeamName() + ": " + p.getDisplayName());
                }

            }
        }

        return true;
    }
}
