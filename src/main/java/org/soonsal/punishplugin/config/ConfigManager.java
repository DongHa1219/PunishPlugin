package org.soonsal.punishplugin.config;

import java.util.HashSet;
import java.util.Set;

public class ConfigManager {
    /**
     * 전체 리로드를 수행할때 리로드되는 목록
     */
    private static final Set<Config> configs = new HashSet<>();

    public static Config config = new Config("config.yml");
    public static Config message = new Config("message.yml");
    public static Config gui = new Config("gui.yml");

    static {
        // register
        configs.add(config);
        configs.add(message);
        configs.add(gui);
    }

    public static void reload() {
        for (Config config : configs) {
            config.pathCheck();
            config.reload();
        }
    }
}
