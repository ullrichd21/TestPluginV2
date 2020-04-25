package com.fallenmoons.testplugin.core;

import com.fallenmoons.testplugin.TestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.*;

public class Round {
    private Plugin plugin = TestPlugin.getPlugin(TestPlugin.class);
    private int duration;
    private int max_duration;

//    private ScoreboardManager manager;
//    private Scoreboard scoreboard;
//    private Objective objective;
//    private Score score;

    private BossBar bar;

    private BukkitScheduler scheduler;
    private int updateTaskId;

    public Round(int duration) {
        this.duration = duration;
        this.max_duration = duration;
//        plugin.getServer().broadcastMessage("A Round has been created.");
//
//        manager = Bukkit.getScoreboardManager();
//        scoreboard = manager.getNewScoreboard();
//
//        objective = scoreboard.registerNewObjective("round",
//                "dummy", ChatColor.AQUA + "Current Round");
//        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//
//        score = objective.getScore(ChatColor.GOLD + "Time Left: " + duration);
//        score.setScore(1);
//
//        for(Player player : plugin.getServer().getOnlinePlayers() ) {
//            player.sendMessage("Added you to scoreboard.");
//            player.setScoreboard(scoreboard);
//        }

        bar = Bukkit.createBossBar(ChatColor.AQUA + "Time Remaining: " + getTimeRemaining(duration), BarColor.RED, BarStyle.SOLID);
        bar.setVisible(true);
        bar.removeAll();

        for(Player player : plugin.getServer().getOnlinePlayers()) {
            bar.addPlayer(player);
            player.playSound(player.getLocation(), Sound.EVENT_RAID_HORN, 100, 1);
            player.playSound(player.getLocation(), Sound.UI_TOAST_IN, 100, 1);
        }


        scheduler = plugin.getServer().getScheduler();
        updateTaskId = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                decreaseTime();
            }
        }, Long.valueOf(20), Long.valueOf(20));
    }

    public void decreaseTime() {
        duration--;
        bar.setProgress(duration/(float) max_duration);

        String hrs = "00";
        String min = "00";
        String sec = "00";

        bar.setTitle(ChatColor.AQUA + "Time Remaining: " + getTimeRemaining(duration));
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            bar.addPlayer(player);
        }

        if (duration == 0) {
            scheduler.cancelTask(updateTaskId);
            bar.removeAll();
            bar.setVisible(false);
            for(Player player : plugin.getServer().getOnlinePlayers()) {
                bar.addPlayer(player);
                player.playSound(player.getLocation(), Sound.UI_TOAST_OUT, 100, 1);
            }
        }
        //plugin.getServer().broadcastMessage("Progress: " + (duration/(float) max_duration));
    }

    public String getTimeRemaining(int time) {
        int hrs = time / 60;
        int min = hrs % 60;
        int sec = time % 60;

        String tStr = "";

        if (hrs < 10) {
            tStr += "0";
        }
        tStr += hrs + ":";

        if (min < 10) {
            tStr += "0";
        }
        tStr += min + ":";

        if (sec < 10) {
            tStr += "0";
        }
        tStr += sec;

        return tStr;
    }

}
