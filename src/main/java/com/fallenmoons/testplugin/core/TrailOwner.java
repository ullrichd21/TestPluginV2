package com.fallenmoons.testplugin.core;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class TrailOwner {
    private Player player;
    private String playerName;
    private Particle part;

    public TrailOwner(Player player, Particle part) {
        this.player = player;
        this.part = part;
        this.playerName = player.getDisplayName();
    }

    public Player getPlayer() {
        return player;
    }

    public Particle getParticle() {
        return part;
    }

    public void setParticle(Particle part) {
        this.part = part;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
