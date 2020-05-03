package com.fallenmoons.mcctf.commands.teamcommands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinTeam implements CommandExecutor {
    private Main main;
    private TeamManager teamManager;

    public JoinTeam(Main main) {
        this.main = main;
        this.teamManager = main.getTeamManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length != 1) {
                return false;
            }

            if (teamManager.teamExists(args[0])) {
                teamManager.joinTeam(teamManager.getTeamFromName(args[0]), (Player) sender);
            } else {
                sender.sendMessage(ChatColor.RED + "Team doesn't exist! Try creating it first!");
            }
        } else {
            main.getLogger().info("You have to be a player to use this command!");
        }

        return true;
    }
}
