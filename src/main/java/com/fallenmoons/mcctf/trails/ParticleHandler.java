package com.fallenmoons.mcctf.trails;

import com.fallenmoons.mcctf.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ParticleHandler implements Listener {

    private Main main;
    private ParticleManager partManager;

    public ParticleHandler(Main main) {
        this.main = main;
        this.partManager = main.getParticleManager();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (partManager.hasPlayer(e.getPlayer())) {
            TrailOwner trail = partManager.getTrailOwner(e.getPlayer());
            e.getPlayer().getWorld().spawnParticle(trail.getParticle(), e.getPlayer().getLocation(), 1);
        }
    }
}
