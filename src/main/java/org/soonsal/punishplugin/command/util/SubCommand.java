package org.soonsal.punishplugin.command.util;

import org.bukkit.entity.Player;

public interface SubCommand {
    String getPermission();

    String getName();

    String getDescription();

    String getSyntax();

    void perform(Player player, String[] args);

    default String[] getSubCommands() {
        return getSyntax().split(" ");
    }
}
