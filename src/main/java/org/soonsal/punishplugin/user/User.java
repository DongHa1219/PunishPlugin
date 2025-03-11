package org.soonsal.punishplugin.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.punish.PunishManager;
import org.soonsal.punishplugin.warning.WarningManger;

import java.util.UUID;

public class User {
    private final UUID uniqueID;
    private final WarningManger warningManger = new WarningManger();
    private final PunishManager punishManager = new PunishManager();

    public User(UUID uniqueID) {
        this.uniqueID = uniqueID;

        UserManager.add(uniqueID, this);
    }

    public WarningManger getWarningManger() {
        return warningManger;
    }

    public PunishManager getPunishManager() {
        return punishManager;
    }

    public boolean isOnline() {
        return getPlayer() != null;
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueID);
    }

    public UUID getUniqueID() {
        return uniqueID;
    }
}
