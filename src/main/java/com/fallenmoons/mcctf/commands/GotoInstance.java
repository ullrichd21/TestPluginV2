package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.FileUtil;

import java.io.File;

public class GotoInstance implements CommandExecutor {
    private Main main;

    public GotoInstance(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 1) {

                if (main.getServer().getWorld(args[0]) != null) {
                    if (main.getServer().getWorld(args[0]).getPlayers().size() == 0) {
                        Bukkit.unloadWorld(args[0], false);
                        main.getLogger().info("Unloaded: " + args[0]);
                    } else {
                        for(Player p : main.getServer().getWorld(args[0]).getPlayers()) {
                            p.kickPlayer("Instance is closed.");
                        }

                        Bukkit.unloadWorld(args[0], false);
                        main.getLogger().info("Unloaded: " + args[0]);
                    }
                }

                World world = new WorldCreator(args[0]).createWorld();
                main.getLogger().info("Getting Host World: " + world.getName());

                File worldDir = world.getWorldFolder();
                String newName = world.getName() + "_instance";
                main.getLogger().info("New Instance World: " + newName);
                FileUtil.copy(worldDir, new File(worldDir.getParent(), newName));

                Location loc = new WorldCreator(newName).createWorld().getSpawnLocation();

                Player player = (Player) sender;
                player.teleport(loc);

                Bukkit.unloadWorld(world, false);
                return true;
            }

        }

        return false;
    }
}
