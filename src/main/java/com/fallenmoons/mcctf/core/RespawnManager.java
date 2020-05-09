package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnManager implements Listener {

    private Main main;

    public RespawnManager(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        if (main.getTeamManager().playerOnTeam(player)) {
            Team t = main.getTeamManager().getTeamFromPlayer(player);
            int teamID = t.getTeamID();
            e.setRespawnLocation(main.getTeamSpawnData().getSpawnLocation(player.getWorld(), teamID, player));
        } else {
            e.setRespawnLocation(player.getWorld().getSpawnLocation());
        }
    }

}
