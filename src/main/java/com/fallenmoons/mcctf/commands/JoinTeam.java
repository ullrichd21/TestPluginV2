package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JoinTeam implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    TeamManager teamManager;

    public JoinTeam(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 1) {
                return false;
            }

            teamManager.joinTeam(teamManager.getTeamFromName(args[0]), (Player) sender);
        } else {
            plugin.getLogger().info("You have to be a player to use this command!");
        }

        return true;
    }
}
