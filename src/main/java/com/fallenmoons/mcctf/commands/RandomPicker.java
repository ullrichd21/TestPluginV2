package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.RandomPickerGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class RandomPicker implements CommandExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            ArrayList<Player> playerList = new ArrayList<Player>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                playerList.add(p);
            }

            new RandomPickerGui(playerList);
        } else {
            plugin.getLogger().info("You must be a player to use this command!");
        }

        return true;
    }
}
