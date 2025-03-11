package org.soonsal.punishplugin.warning;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.soonsal.punishplugin.data.DataManager;

import java.io.File;
import java.io.IOException;

public class WarningIdProvider {
    private static WarningIdProvider instance;
    private final File file;
    private final FileConfiguration config;

    public static WarningIdProvider getInstance() {
        if (instance == null) {
            instance = new WarningIdProvider();
        }

        return instance;
    }

    public WarningIdProvider() {
        this.file = new File(DataManager.DATA_FOLDER.getFolderFile(), "warningId.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public int generate() {
        int newID = config.isInt("value") ? (config.getInt("value") + 1) : 1;

        try {
            config.set("value", newID);
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newID;
    }
}
