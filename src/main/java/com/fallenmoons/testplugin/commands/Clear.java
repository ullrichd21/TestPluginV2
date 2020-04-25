package com.fallenmoons.testplugin.commands;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Clear implements CommandExecutor {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
           Player player = (Player) sender;
           player.getInventory().clear();
           player.sendMessage(ChatColor.GREEN + "Your inventory has been cleared!");
        } else {
            plugin.getLogger().info("You need to be a player to use this command!");
        }

        return true;
    }
}
