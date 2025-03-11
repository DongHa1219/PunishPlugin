package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.config.ConfigManager;
import org.soonsal.punishplugin.util.Info;

public class ConfigReloadCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.CONFIG_RELOAD.getPermission();
    }

    @Override
    public String getName() {
        return "콘피그리로드";
    }

    @Override
    public String getDescription() {
        return "콘피그를 리로드합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 콘피그리로드";
    }

    @Override
    public void perform(Player player, String[] args) {
        ConfigManager.reload();
        Info.sendMessage(player, "콘피그가 리로드되었습니다.");
    }
}
