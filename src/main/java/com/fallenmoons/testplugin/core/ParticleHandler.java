package com.fallenmoons.testplugin.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParticleHandler implements Listener {

    private ParticleManager partManager;

    public ParticleHandler(ParticleManager partManager) {
        this.partManager = partManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (partManager.hasPlayer(e.getPlayer())) {
            TrailOwner trail = partManager.getTrailOwner(e.getPlayer());
            e.getPlayer().getWorld().spawnParticle(trail.getParticle(), e.getPlayer().getLocation(), 1);
        }
    }
}
