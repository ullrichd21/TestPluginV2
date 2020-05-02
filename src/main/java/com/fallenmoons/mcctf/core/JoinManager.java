package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinManager implements Listener {
    private Main main;

    public JoinManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        //main.getLogger().info("Player Joined!");

        if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("lobby")) {
            e.getPlayer().teleport(main.getServer().getWorld("lobby").getSpawnLocation());
            e.getPlayer().sendMessage(ChatColor.RED + "You joined the server in an instance that no "
                    + "longer exists! Returning you to the lobby!");
            main.getLogger().info("Moved " + e.getPlayer().getDisplayName() + " to the lobby.");
        }
    }
}
