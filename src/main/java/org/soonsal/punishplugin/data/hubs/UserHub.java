package org.soonsal.punishplugin.data.hubs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.soonsal.punishplugin.data.DataManager;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.Folder;
import org.soonsal.punishplugin.util.format.DateFormat;
import org.soonsal.punishplugin.warning.Warning;
import org.soonsal.punishplugin.warning.WarningBuilder;
import org.soonsal.punishplugin.warning.WarningType;

import java.io.File;
import java.util.UUID;

public class UserHub implements Hub<User> {
    private final Folder userFolder = new Folder(DataManager.DATA_FOLDER, "users");

    @Override
    public void save(User user) {
        File file = new File(userFolder.getFolderFile(), user.getUniqueID().toString() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("uuid", user.getUniqueID().toString());

        ConfigurationSection warningSection = config.createSection("warning");
        for (Warning warning : user.getWarningManger().getWarnings()) {
            ConfigurationSection section = warningSection.createSection(String.valueOf(warning.getId()));

            section.set("warningType", warning.getWarningType().name());
            section.set("from", warning.getFrom().toString());
            section.set("to", warning.getTo().toString());
            section.set("date", DateFormat.of(warning.getDate()));
            section.set("punishType", warning.getPunishType().name());
            section.set("reason", warning.getReason());
        }

        ConfigurationSection punishSection = config.createSection("punish");
        int order = 0;
        for (Punish punish : user.getPunishManager().getPunishes()) {
            ConfigurationSection section = punishSection.createSection(String.valueOf(order++));

            section.set("warningCount", punish.getWarningCount());
            section.set("type", punish.getType().name());
            section.set("duration", punish.getDuration());
            section.set("start", punish.getStart());
        }

        save(file, config);
    }

    @SuppressWarnings("all")
    @Override
    public User load(String var) {
        File file = new File(userFolder.getFolderFile(), var + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        UUID uuid = UUID.fromString(var);
        User user = new User(uuid);

        ConfigurationSection warningSection = config.getConfigurationSection("warning");
        if (warningSection != null) {
            for (String key : warningSection.getKeys(false)) {
                ConfigurationSection section = warningSection.getConfigurationSection(key);

                WarningBuilder.builder()
                        .setID(Integer.parseInt(key))
                        .setWarningType(WarningType.valueOf(section.getString("warningType")))
                        .setFrom(UUID.fromString(section.getString("from")))
                        .setTo(UUID.fromString(section.getString("to")))
                        .setDate(DateFormat.parse(section.getString("date")))
                        .setPunishType(PunishType.valueOf(section.getString("punishType")))
                        .setReason(section.getString("reason"))
                        .withApply(user.getWarningManger())
                        .build();
            }
        }

        ConfigurationSection punishSection = config.getConfigurationSection("punish");
        if (punishSection != null) {
            for (String key : punishSection.getKeys(false)) {
                ConfigurationSection section = punishSection.getConfigurationSection(key);

                PunishType punishType = PunishType.valueOf(section.getString("type"));
                int warningCount = section.getInt("warningCount");
                int duration = section.getInt("duration");
                long start = section.getLong("start");

                Punish punish = Punish.newPunish(punishType, user, warningCount, duration, start);
                user.getPunishManager().addPunish(punish);
            }
        }

        return user;
    }

    @Override
    public void loadAll() {
        for (File file : userFolder.getFiles()) {
            load(file.getName().split(".")[0]);
        }
    }

    @Override
    public void saveAll() {
        for (User user : UserManager.getUsers()) {
            save(user);
        }
    }
}
