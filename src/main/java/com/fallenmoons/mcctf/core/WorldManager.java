package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class WorldManager {

    //private Main main;

    public WorldManager(Main main) {

        //Set up instance resetting:
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {

            boolean alreadyReset;
            @Override
            public void run() {
                List<World> worlds = Bukkit.getWorlds();

                for(World w : worlds) {
                    if (w.getPlayers().size() == 0 && w.getName().contains("_instance") && alreadyReset == false) {
                        Bukkit.unloadWorld(w, false);
                        alreadyReset = true;
                        main.getLogger().info("Resetting and Unloading World \"" + w.getName() + "\".");
                    } else if (w.getPlayers().size() > 0 && w.getName().contains("_instance") && alreadyReset == true) {
                        alreadyReset = false;
                    }
                }
            }
        },0L, 10L);


        //Set up autosaving for regular worlds:
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                List<World> worlds = Bukkit.getWorlds();

                for(World w : worlds) {
                    if (w.getPlayers().size() > 0 && !w.getName().contains("_instance")) {
                        w.save();
                        main.getLogger().info("Autosaving World \"" + w.getName() + "\".");
                    }
                }
            }
        },12000L, 12000L);
    }
}
