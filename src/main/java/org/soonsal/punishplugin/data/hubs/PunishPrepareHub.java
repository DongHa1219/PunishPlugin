package org.soonsal.punishplugin.data.hubs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.soonsal.punishplugin.data.DataManager;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.punish.prepare.PunishPrepare;
import org.soonsal.punishplugin.punish.prepare.PunishPrepareManager;
import org.soonsal.punishplugin.util.Folder;

import java.io.File;

public class PunishPrepareHub implements Hub<PunishPrepare> {
    private final Folder folder = DataManager.DATA_FOLDER;

    @Override
    public void saveAll() {
        File file = new File(folder.getFolderFile(), "punish.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (PunishPrepare prepare : PunishPrepareManager.getPunishPrepares()) {
            ConfigurationSection section = config.createSection(String.valueOf(prepare.getCount()));

            section.set("type", prepare.getType().name());
            section.set("duration", prepare.getDuration());
        }

        save(file, config);
    }

    @SuppressWarnings("all")
    @Override
    public void loadAll() {
        File file = new File(folder.getFolderFile(), "punish.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            ConfigurationSection section = config.getConfigurationSection(key);

            int count = Integer.parseInt(key);
            PunishType punishType = PunishType.valueOf(section.getString("type"));
            int duration = Integer.parseInt(section.getString("duration"));

            new PunishPrepare(punishType, count, duration);
        }
    }

    @Override
    public void save(PunishPrepare prepare) {
        // not used
    }

    @Override
    public PunishPrepare load(String name) {
        // not used
        return null;
    }
}
