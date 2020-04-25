package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import com.fallenmoons.testplugin.core.Round;
import com.fallenmoons.testplugin.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Startround implements CommandExecutor {
    Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        plugin.getLogger().info("A new round has been started!");
        plugin.getServer().broadcastMessage(ChatColor.AQUA + "A new round has been started!");

        TeamManager teams = new TeamManager(new String[] {"Blue", "Red", "Green"});

        if (args.length > 0) {
            Round currentRound = new Round(Integer.valueOf(args[0]));
        } else {
            Round currentRound = new Round(30);
        }

        return true;
    }
}
