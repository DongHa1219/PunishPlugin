package org.soonsal.punishplugin.command.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.soonsal.punishplugin.util.Info;

public abstract class SoonSaLCommand implements CommandExecutor {
    public abstract void runCommand(Player player, String label, String[] args);

    public abstract String getLabel();

    public abstract boolean isAdminCommand();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return true;
        }

        if (isAdminCommand() && !player.isOp()) {
            Info.sendMessage(player, "권한이 부족합니다.");
            return true;
        }

        runCommand(player, s, strings);

        return true;
    }
}
