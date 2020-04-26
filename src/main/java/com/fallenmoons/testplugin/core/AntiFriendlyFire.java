package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class AntiFriendlyFire implements Listener {

    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);

    private TeamManager teamManager;

    public AntiFriendlyFire(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @EventHandler
    public void onFriendlyFire(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player attacker = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();
            Team attackerTeam = teamManager.playerOnTeam(attacker);
            Team damagedTeam = teamManager.playerOnTeam(damaged);

            if (!attackerTeam.equals(null) && !damagedTeam.equals(null)) {
                if(attackerTeam.equals(damagedTeam)) {
                    e.setCancelled(true);
                    attacker.sendMessage(ChatColor.RED + damaged.getDisplayName() + " is on your team!");
                }
            }
        }
    }
}
