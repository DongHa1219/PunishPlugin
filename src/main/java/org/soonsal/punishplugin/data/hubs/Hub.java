package org.soonsal.punishplugin.data.hubs;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("UnusedReturnValue")
public interface Hub<T> {
    void save(T t);

    T load(String name);

    void loadAll();

    void saveAll();

    default void save(File file, FileConfiguration config) {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
