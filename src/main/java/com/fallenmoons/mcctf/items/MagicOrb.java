package com.fallenmoons.mcctf.items;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MagicOrb implements Listener {

    @EventHandler
    public void onUseOrb(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getItem().hasItemMeta()) {
            if (e.getItem().getItemMeta().getCustomModelData() == 1) {
                Projectile orb = (Projectile) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.SNOWBALL);

                orb.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(1));
                orb.setCustomName("Orb");
                orb.setCustomNameVisible(true);
            }
        }
    }
}
