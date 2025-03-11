package org.soonsal.punishplugin.config.list;

import org.soonsal.punishplugin.config.ConfigManager;
import org.soonsal.punishplugin.util.MessageList;
import org.soonsal.punishplugin.util.PathBuilder;
import org.soonsal.punishplugin.util.Util;

import java.util.List;
import java.util.stream.Collectors;

public enum Config {
    WARNING_BROADCAST,
    DATE_FORMAT,
    BLIND_CHAT_PERIOD,
    ;

    public MessageList getStringList(String... path) {
        List<String> list = ConfigManager.config.getConfig().getStringList(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ).stream().map(Util::color).collect(Collectors.toList());
        return new MessageList(list);
    }

    public int getInt(String... path) {
        return ConfigManager.config.getConfig().getInt(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }

    public boolean getBoolean(String... path) {
        return ConfigManager.config.getConfig().getBoolean(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }

    public String getString(String... path) {
        return Util.color(ConfigManager.config.getConfig().getString(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        ));
    }

    public double getDouble(String... path) {
        return ConfigManager.config.getConfig().getDouble(
                PathBuilder.builder()
                        .append(name())
                        .append(path)
                        .assemble()
        );
    }
}
