package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.gui.list.PunishSettingGUI;

public class PunishSettingCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.SETTING.getPermission();
    }

    @Override
    public String getName() {
        return "처벌설정";
    }

    @Override
    public String getDescription() {
        return "목표 경고 도달시 적용할 처벌을 설정합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 처벌설정";
    }

    @Override
    public void perform(Player player, String[] args) {
        PunishSettingGUI settingGUI = new PunishSettingGUI();
        settingGUI.open(player);
    }
}
