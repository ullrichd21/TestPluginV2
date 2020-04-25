package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class AntiLogStripper implements Listener {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    @EventHandler
    public void onStripLogAttempt(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("axe")
                    && e.getClickedBlock().getType().toString().toLowerCase().contains("log")) {
                e.setCancelled(true);
                player.sendMessage(ChatColor.RED + "There ain't gon' be none of that in these here parts...");
            }
        }
    }
}
