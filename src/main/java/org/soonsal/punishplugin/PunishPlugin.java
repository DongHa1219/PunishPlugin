package org.soonsal.punishplugin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.soonsal.punishplugin.command.admin.WarningAdminCommand;
import org.soonsal.punishplugin.command.user.WarningUserCommand;
import org.soonsal.punishplugin.command.util.SoonSaLCommand;
import org.soonsal.punishplugin.config.ConfigManager;
import org.soonsal.punishplugin.data.DataManager;
import org.soonsal.punishplugin.gui.listener.GUIListener;
import org.soonsal.punishplugin.punish.PunishListener;
import org.soonsal.punishplugin.listener.UserDataListener;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.version.VersionHandler;
import org.soonsal.punishplugin.version.VersionListener;

import java.util.Objects;

public final class PunishPlugin extends JavaPlugin {
    private static PunishPlugin plugin;

    private DataManager dataManager;

    @Override
    public void onEnable() {
        plugin = this;

        initEvent();
        initCommand();

        ConfigManager.reload();
        VersionHandler.init();

        dataManager = new DataManager();
        dataManager.load();
    }

    @Override
    public void onDisable() {
        dataManager.saveAll();
    }

    public static PunishPlugin getInstance() {
        return plugin;
    }

    private void initEvent() {
        registerEvent(new GUIListener());
        registerEvent(new PunishListener());
        registerEvent(new UserDataListener());
        registerEvent(new VersionListener());
    }

    private void initCommand() {
        registerCommand(new WarningAdminCommand());
        registerCommand(new WarningUserCommand());
    }

    private void registerCommand(SoonSaLCommand commandExecutor) {
        registerCommand(commandExecutor.getLabel(), commandExecutor);
    }

    @SuppressWarnings("all")
    private void registerCommand(String command, CommandExecutor commandExecutor) {
        Objects.requireNonNull(getCommand(command)).setExecutor(commandExecutor);

        if (commandExecutor instanceof TabCompleter) {
            Objects.requireNonNull(getCommand(command)).setTabCompleter((TabCompleter) commandExecutor);
        }
    }

    private void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
