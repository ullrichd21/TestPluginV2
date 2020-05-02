package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.Round;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Startround implements CommandExecutor {
    Plugin plugin = Main.getPlugin(Main.class);
    TeamManager teamManager;

    public Startround(TeamManager manager) {
        this.teamManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.getLogger().info("A new round has been started!");
        plugin.getServer().broadcastMessage(ChatColor.AQUA + "A new round has been started!");



        if (args.length > 0) {
            Round currentRound = new Round(Integer.valueOf(args[0]));
        } else {
            Round currentRound = new Round(30);
        }

        return true;
    }
}
