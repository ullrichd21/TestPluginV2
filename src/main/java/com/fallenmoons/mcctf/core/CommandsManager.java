package com.fallenmoons.mcctf.core;

import com.fallenmoons.mcctf.Main;
import com.fallenmoons.mcctf.commands.*;

public class CommandsManager {

    private Main main;

    public CommandsManager(Main main) {
        this.main = main;

        main.getCommand("clear").setExecutor(new Clear());
        main.getCommand("setspawn").setExecutor(new Setspawn());
        main.getCommand("spawn").setExecutor(new Spawn());
        main.getCommand("startround").setExecutor(new Startround(main.getTeamManager()));
        main.getCommand("gui").setExecutor(new Gui());
        main.getCommand("jointeam").setExecutor(new JoinTeam(main.getTeamManager()));
        main.getCommand("viewteams").setExecutor(new ViewTeams(main.getTeamManager()));
        main.getCommand("hat").setExecutor(new Hat());
        main.getCommand("trail").setExecutor(new Trail(main.getParticleManager()));
        main.getCommand("createflag").setExecutor(new CreateFlag());
        main.getCommand("feed").setExecutor(new Feed());
        main.getCommand("heal").setExecutor(new Heal());
        main.getCommand("randompicker").setExecutor(new RandomPicker());
        main.getCommand("world").setExecutor(new WorldCommand(main));
        main.getCommand("gotoinstance").setExecutor(new GotoInstance(main));
        main.getCommand("createteamspawn").setExecutor(new CreateTeamSpawn(main));
        main.getCommand("givecustom").setExecutor(new GiveCustom(main));
    }
}
