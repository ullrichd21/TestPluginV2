package com.fallenmoons.mcctf.commands;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.trails.ParticleManager;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Trail implements CommandExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);
    private ParticleManager partManager;
    public Trail(ParticleManager partManager) {
        this.partManager = partManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1) {
                Particle part = Particle.BARRIER;
                try {
                    part = Particle.valueOf(args[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    player.sendMessage("That's not a valid particle!");
                    return false;
                }

                if (partManager.hasPlayer(player)) {
                    partManager.getTrailOwner(player).setParticle(part);
                } else {
                    partManager.togglePlayer(player, part);
                }
            } else {
                partManager.togglePlayer(player);
            }
        } else {
            plugin.getLogger().info("You must be a player to use this command!");
        }

        return true;
    }
}
