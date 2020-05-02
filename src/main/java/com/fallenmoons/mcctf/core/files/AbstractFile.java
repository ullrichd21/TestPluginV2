package com.fallenmoons.mcctf.core.files;

import com.fallenmoons.mcctf.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

public class AbstractFile {

    protected Main main;
    private File file;
    private String fileName;
    protected FileConfiguration config;

    public AbstractFile(Main main, String fileName) {
        this.fileName = fileName;
        this.file = new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
