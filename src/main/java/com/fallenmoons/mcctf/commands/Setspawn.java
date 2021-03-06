package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Setspawn implements CommandExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getWorld().setSpawnLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "New world spawn set!");
        } else {
            plugin.getLogger().info("You need to be a player to use this command!");
        }

        return true;
    }
}
