package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Team {

    private Plugin plugin = Main.getPlugin(Main.class);

    private int teamID;
    private String teamName;
    private ArrayList<Player> players;
    private ChatColor teamColor;
    private int points;
    private int coins;

    //Team Storehouse Numbers
    private int wood;
    private int cobblestone;
    private int iron;
    private int gold;
    private int diamonds;

    public Team(String teamName, ChatColor teamColor, int teamID) {
        this.teamName = teamName;
        this.teamID = teamID;
        players = new ArrayList<Player>();
        this.teamColor = teamColor;
        this.points = 0;
        this.coins = 0;
        this.wood = 0;
        this.cobblestone = 0;
        this.iron = 0;
        this.gold = 0;
        this.diamonds = 0;
    }

    public Team(ChatColor teamColor) {
        this.teamName = "Unknown";
        players = new ArrayList<Player>();
        this.teamColor = teamColor;
    }

    public ArrayList<Player> getMembers() {
        return players;
    }

    public int getNumberOfMembers() {
        return players.size();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamColor(ChatColor teamColor) {
        this.teamColor = teamColor;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ChatColor getTeamColor() {
        return teamColor;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public int getCobblestone() {
        return cobblestone;
    }

    public void setCobblestone(int cobblestone) {
        this.cobblestone = cobblestone;
    }

    public int getIron() {
        return iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public void joinTeam(Player player) {
        players.add(player);
        player.setDisplayName(teamColor + player.getDisplayName() + ChatColor.WHITE);
        player.setPlayerListName(teamColor + player.getDisplayName() + ChatColor.WHITE);
    }

}
