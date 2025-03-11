package org.soonsal.punishplugin.user.permission;

import org.bukkit.entity.Player;

public enum PunishPermission {

    ;

    private final String permission;

    PunishPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }
}
