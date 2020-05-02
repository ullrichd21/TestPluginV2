package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {

    private Main main;

    public WorldCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 1) {
                Player player = (Player) sender;
                String worldName = args[0];

                if (main.getServer().getWorld(worldName) == null) {
                    Location loc = new WorldCreator(worldName).createWorld().getSpawnLocation();
                    player.teleport(loc);
                } else {
                    Location loc = main.getServer().getWorld(worldName).getSpawnLocation();
                    player.teleport(loc);
                }

                if (main.getServer().getWorld(worldName).getPlayers().size() == 0) {
                    if (worldName.contains("_instance")){
                        Bukkit.unloadWorld(main.getServer().getWorld(worldName), false);
                    } else {
                        Bukkit.unloadWorld(main.getServer().getWorld(worldName), true);
                    }

                }

            } else {
                return false;
            }

        }

        return true;
    }
}
