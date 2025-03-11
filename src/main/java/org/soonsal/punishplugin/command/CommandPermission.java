package org.soonsal.punishplugin.command;

import org.bukkit.entity.Player;

public enum CommandPermission {
    SETTING("warning.setting"),
    INCREASE("warning.increase"),
    DECREASE("warning.decrease"),
    CONFIRM("warning.confirm"),
    RESET("warning.reset"),
    PARDON("warning.pardon"),
    CONFIG_RELOAD("warning.configReload"),
    ;

    private final String permission;

    CommandPermission(String permission) {
        this.permission = permission;
    }

    public boolean hasPermission(Player player) {
        return player.hasPermission(permission);
    }

    public static boolean hasAnyPermission(Player player) {
        for (CommandPermission permission : CommandPermission.values()) {
            if (permission.hasPermission(player)) {
                return true;
            }
        }

        return false;
    }

    public String getPermission() {
        return permission;
    }
}
