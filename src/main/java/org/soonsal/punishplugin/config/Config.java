package org.soonsal.punishplugin.config;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.util.ItemUtil;
import org.soonsal.punishplugin.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Config {
    private final String fileName;
    private final File file;
    private FileConfiguration config;

    public Config(String fileName) {
        this.fileName = fileName;

        file = new File(PunishPlugin.getInstance().getDataFolder(), fileName);

        reload();
        save();
    }

    public void pathCheck() {
        InputStream inputStream = PunishPlugin.getInstance().getResource(fileName);
        if (inputStream != null) {
            FileConfiguration originalConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            for (String key : originalConfig.getKeys(true)) {
                if (!config.contains(key) || config.get(key) == null) {
                    config.set(key, originalConfig.get(key));
                }
            }
            save();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @SuppressWarnings("all")
    public void reload() {
        if (!file.exists()) {
            final File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            final InputStream defaultSteam = PunishPlugin.getInstance().getResource(fileName);
            if (defaultSteam != null) {
                try {
                    Files.copy(defaultSteam, file.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public @NotNull ItemStack getPlayerHeadItem(OfflinePlayer offlinePlayer, String path) {
        String title = config.getString(path + ".display");
        List<String> lore = config.getStringList(path + ".lore");

        return ItemUtil.craftPlayerHead(offlinePlayer, title, lore);
    }

    public @NotNull ItemStack getItem(String path) {
        String title = config.getString(path + ".display");
        String materialStr = config.getString(path + ".material");
        int modelData = config.getInt(path + ".modeldata");
        List<String> lore = config.getStringList(path + ".lore");

        if (title == null) {
            title = "제목을 가져오지 못했습니다.";
        }
        if (materialStr == null || !Util.isMaterial(materialStr)) {
            materialStr = Material.STONE.name();
            PunishPlugin.getInstance().getLogger().warning(fileName + "에서 material 정보를 가져오지 못했습니다.");
            PunishPlugin.getInstance().getLogger().warning("위치: " + path);
        }

        Material material = Material.valueOf(materialStr);

        return ItemUtil.craft(material, 1, title, lore, modelData);
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
