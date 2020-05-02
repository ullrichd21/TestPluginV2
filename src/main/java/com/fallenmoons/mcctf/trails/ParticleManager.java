package com.fallenmoons.mcctf.trails;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ParticleManager {
    ArrayList<TrailOwner> playerList;

    public ParticleManager() {
        playerList = new ArrayList<TrailOwner>();
    }

    public boolean hasPlayer(Player player) {
        if (playerList.size() > 0) {
            for (TrailOwner tOwner : playerList) {
                if (tOwner.getPlayer().equals(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public TrailOwner getTrailOwner(Player player) {
        for(TrailOwner tOwner : playerList) {
            if (tOwner.getPlayer().equals(player)) {
                return tOwner;
            }
        }
        return null;
    }

    public void togglePlayer(Player player, Particle part) {
        if (playerList.size() > 0) {
            for (TrailOwner tOwner : playerList) {
                if (tOwner.getPlayer().equals(player)) {
                    playerList.remove(tOwner);
                    return;
                }
            }
        }

        playerList.add(new TrailOwner(player, part));
    }

    public void togglePlayer(Player player) {

        for(TrailOwner tOwner : playerList) {
            if (tOwner.getPlayer().equals(player)) {
                playerList.remove(tOwner);
                return;
            }
        }
    }
}
