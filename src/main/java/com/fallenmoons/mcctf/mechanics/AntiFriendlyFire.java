package com.fallenmoons.mcctf.mechanics;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.core.Team;
import com.fallenmoons.mcctf.core.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiFriendlyFire implements Listener {

    private Main main;

    private final TeamManager teamManager;

    public AntiFriendlyFire(Main main) {
        this.main = main;
        this.teamManager = this.main.getTeamManager();
    }

    @EventHandler
    public void onFriendlyFire(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player attacker = (Player) e.getDamager();
            Player damaged = (Player) e.getEntity();
            Team attackerTeam = teamManager.getTeamFromPlayer(attacker);
            Team damagedTeam = teamManager.getTeamFromPlayer(damaged);

//            attacker.sendMessage("You attacked " + damaged.getDisplayName());
//            damaged.sendMessage("You've been attacked by " + attacker.getDisplayName());

            if (!attackerTeam.equals(null) && !damagedTeam.equals(null)) {
                if(attackerTeam.equals(damagedTeam)) {
                    e.setCancelled(true);
                    attacker.sendMessage(ChatColor.RED + damaged.getDisplayName() + " is on your team!");
                }
            }
        }
    }
}
