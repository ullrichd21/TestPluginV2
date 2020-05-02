package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveWorld implements CommandExecutor {

    private Main main;

    public SaveWorld(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                saveWorld(player.getWorld());
            } else if (args.length == 1){
                if (main.getServer().getWorld(args[0]) != null) {
                    saveWorld(args[0]);
                    return true;
                } else {
                    player.sendMessage(ChatColor.RED + "World doesn't exist!");
                    return false;
                }
            } else {
                player.sendMessage(ChatColor.RED + "Too many arguments!");
                return false;
            }

        } else if (args.length == 0) {
            main.getLogger().info("Missing a world name!");
            return false;
        } else if (args.length == 1) {
            if (main.getServer().getWorld(args[0]) != null) {
                saveWorld(args[0]);
                return true;
            } else {
                main.getLogger().info("World doesn't exist!");
                return false;
            }
        }

        return false;
    }

    private void saveWorld(String worldName) {
        main.getServer().getWorld(worldName).save();
    }

    private void saveWorld(World world) {
        main.getServer().getWorld(world.getName()).save();
    }
}
