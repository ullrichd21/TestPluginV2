package com.fallenmoons.mcctf.core.files;

import com.fallenmoons.mcctf.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TeamSpawns extends AbstractFile {

    public TeamSpawns(Main main) {
        super(main, "teamspawns.yml");
    }

    public void saveLocation(World world, String teamName, Vector value) {
        String path = world.getName().toLowerCase() + "." + teamName.toLowerCase();
        config.set(path + ".location", value);
        config.set(path + ".direction", new Vector().zero());
        save();
    }

    public void saveLocation(World world, int teamID, Vector value, Vector direction) {
        String path = world.getName().toLowerCase() + "." + teamID;
        config.set(path + ".location", value);
        config.set(path + ".direction", direction);
        save();
    }

    public Location getSpawnLocation(World world, int teamID, Player player) {
        String path = world.getName().toString().toLowerCase() + "." + teamID;
        Location loc = config.getVector(path + ".location").toLocation(player.getWorld());
        loc.setDirection(config.getVector(path +".direction"));
        return loc;
    }
}
