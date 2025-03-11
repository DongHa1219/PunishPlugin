package org.soonsal.punishplugin.config.list;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.soonsal.punishplugin.config.ConfigManager;
import org.soonsal.punishplugin.util.PathBuilder;
import org.soonsal.punishplugin.util.Util;

import java.util.List;
import java.util.stream.Collectors;

public enum GUI {
    WARNING_LIST_GUI,
    ;

    public String getTitle() {
        return getString("title");
    }

    public ItemStack getItem(String... path) {
        return ConfigManager.gui.getItem(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }

    public ItemStack getPlayerHeadItem(OfflinePlayer offlinePlayer, String... path) {
        return ConfigManager.gui.getPlayerHeadItem(offlinePlayer,
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }

    public List<String> getStringList(String... path) {
        return ConfigManager.gui.getConfig().getStringList(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ).stream().map(Util::color).collect(Collectors.toList());
    }

    public String getString(String... path) {
        return Util.color(ConfigManager.gui.getConfig().getString(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ));
    }

    public int getInt(String... path) {
        return ConfigManager.gui.getConfig().getInt(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }
}
