package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class JoinTeam implements CommandExecutor {
    Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

        } else {
            plugin.getLogger().info("You have to be a player to use this command!");
        }

        return true;
    }
}
