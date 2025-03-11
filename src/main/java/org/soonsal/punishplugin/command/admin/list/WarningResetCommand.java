package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.CommandUtil;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.PlayerUtil;

public class WarningResetCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.RESET.getPermission();
    }

    @Override
    public String getName() {
        return "초기화";
    }

    @Override
    public String getDescription() {
        return "해당 플레이어가 받은 경고들을 초기화합니다. (진행중인 처벌도 사라집니다.)";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 초기화 <플레이어>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (CommandUtil.argsCheck(2, args.length, player)) {
            return;
        }
        if (CommandUtil.playerExistCheck(args[1], player)) {
            return;
        }

        OfflinePlayer offlinePlayer = PlayerUtil.getOfflinePlayer(args[1]);
        UserManager.thenAcceptAsync(offlinePlayer.getUniqueId(), user -> {
            user.getWarningManger().clear();
            user.getPunishManager().clear();

            Info.sendMessage(player, offlinePlayer.getName() + "님의 경고목록을 초기화했습니다.");
            Info.sendMessage(offlinePlayer, Message.WARNING_RESET.getMessage());
        });
    }
}
