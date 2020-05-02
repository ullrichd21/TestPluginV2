package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.items.GrapplingHook;
import com.fallenmoons.mcctf.mechanics.AntiFriendlyFire;
import com.fallenmoons.mcctf.mechanics.AntiLogStripper;
import com.fallenmoons.mcctf.mechanics.cauldron.CauldronManager;
import com.fallenmoons.mcctf.mechanics.customdrops.CustomDropsManager;
import com.fallenmoons.mcctf.mechanics.flags.FlagManager;
import com.fallenmoons.mcctf.mechanics.woodcutter.WoodCutter;
import com.fallenmoons.mcctf.trails.ParticleHandler;

public class ListenerManager {

    private Main main;

    public ListenerManager(Main main) {
        this.main = main;

        //Register Our Log Anti-Stripper. None of that R rated stripping here!
        main.getServer().getPluginManager().registerEvents(new AntiLogStripper(), main);

        //Register Cauldron Mechanics
        main.getServer().getPluginManager().registerEvents(new CauldronManager(), main);

        //Register GUI
        main.getServer().getPluginManager().registerEvents(new GuiClickEvent(), main);

        //Register WoodCutter Mechanics
        main.getServer().getPluginManager().registerEvents(new WoodCutter(), main);

        //Register Anti-Friendly Fire Mechanics
        main.getServer().getPluginManager().registerEvents(new AntiFriendlyFire(main), main);

        //Register ParticleHandler
        main.getServer().getPluginManager().registerEvents(new ParticleHandler(main), main);

        //Register FlagManager
        main.getServer().getPluginManager().registerEvents(new FlagManager(main), main);

        //Register Items
        main.getServer().getPluginManager().registerEvents(new GrapplingHook(), main);

        //Register Join Manager
        main.getServer().getPluginManager().registerEvents(new JoinManager(main), main);

        //Register Custom Drops Manager
        main.getServer().getPluginManager().registerEvents(new CustomDropsManager(), main);

        //Register Respawn Manager
        main.getServer().getPluginManager().registerEvents(new RespawnManager(main), main);
    }
}
