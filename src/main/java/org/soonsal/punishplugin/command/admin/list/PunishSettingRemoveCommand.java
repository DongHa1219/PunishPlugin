package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.CommandUtil;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.punish.prepare.PunishPrepareManager;
import org.soonsal.punishplugin.util.Info;

public class PunishSettingRemoveCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.SETTING.getPermission();
    }

    @Override
    public String getName() {
        return "처벌삭제";
    }

    @Override
    public String getDescription() {
        return "해당 경고 도달시 적용되는 처벌을 삭제합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 처벌삭제 <횟수>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (CommandUtil.argsCheck(2, args.length, player)) {
            return;
        }
        if (CommandUtil.intCheck(args[1], player)) {
            return;
        }

        int count = Integer.parseInt(args[1]);

        if (!PunishPrepareManager.has(count)) {
            Info.sendMessage(player, "해당 경고에 설정된 처벌이 존재하지 않습니다.");
            return;
        }

        PunishPrepareManager.remove(count);
        Info.sendMessage(player, "성공적으로 처벌이 삭제되었습니다.");
    }
}
