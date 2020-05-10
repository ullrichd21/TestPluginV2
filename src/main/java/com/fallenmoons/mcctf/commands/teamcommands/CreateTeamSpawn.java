package com.fallenmoons.mcctf.commands.teamcommands;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateTeamSpawn implements CommandExecutor {

    private Main main;

    public CreateTeamSpawn(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                Location loc = player.getLocation().getBlock().getLocation().add(0.5,0,0.5);
                main.getTeamSpawnData().saveLocation(player.getWorld(), Integer.valueOf(args[0]), loc.toVector(), player.getLocation().getDirection());
                player.sendMessage(ChatColor.GREEN + "Saved Location ("
                        + loc.getX() + ", "
                        + loc.getY() + ", "
                        + loc.getZ() + ") to the config for team \"" + args[0] + "\"!");
                return true;
            }
        }

        return false;
    }
}
