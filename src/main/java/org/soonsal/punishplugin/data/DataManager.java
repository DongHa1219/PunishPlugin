package org.soonsal.punishplugin.data;

import org.soonsal.punishplugin.data.hubs.Hub;
import org.soonsal.punishplugin.data.hubs.PunishPrepareHub;
import org.soonsal.punishplugin.data.hubs.UserHub;
import org.soonsal.punishplugin.util.Folder;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static final Folder DATA_FOLDER = new Folder("data");
    private final List<Hub<?>> hubs = new ArrayList<>();

    // hub list
    private final UserHub userHub = new UserHub();
    private final PunishPrepareHub punishPrepareHub = new PunishPrepareHub();

    public DataManager() {
        // register
        hubs.add(userHub);
        hubs.add(punishPrepareHub);
    }

    public void loadAll() {
        for (Hub<?> hub : hubs) {
            hub.loadAll();
        }
    }

    public void load() {
        punishPrepareHub.loadAll();
    }

    public void unload() {
        saveAll();
    }

    public void saveAll() {
        for (Hub<?> hub : hubs) {
            hub.saveAll();
        }
    }

    public UserHub getUserHub() {
        return userHub;
    }

    public PunishPrepareHub getPunishPrepareHub() {
        return punishPrepareHub;
    }
}
