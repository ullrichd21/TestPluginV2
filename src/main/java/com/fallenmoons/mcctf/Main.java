package com.fallenmoons.mcctf;

import com.fallenmoons.mcctf.core.*;
import com.fallenmoons.mcctf.core.files.TeamData;
import com.fallenmoons.mcctf.trails.ParticleManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin implements Listener {

    private TeamManager teamManager;
    private ParticleManager partManager;
    private TeamData teamSpawnData;

    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }


        //Create Team
        teamSpawnData = new TeamData(this);

        //Create Team Manager
        teamManager = new TeamManager(this);

        //Create particle manager
        partManager = new ParticleManager();



        //Register Commands
        new CommandsManager(this);

        //Register World Manager
        new WorldManager(this);

        //Register Listener Manager
        new ListenerManager(this);
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ParticleManager getParticleManager() {
        return partManager;
    }

    public TeamData getTeamSpawnData() {
        return teamSpawnData;
    }
}
