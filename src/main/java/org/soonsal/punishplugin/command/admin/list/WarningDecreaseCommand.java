package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.CommandUtil;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.config.list.Config;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.PlayerUtil;
import org.soonsal.punishplugin.util.Util;
import org.soonsal.punishplugin.warning.WarningHandler;

public class WarningDecreaseCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.DECREASE.getPermission();
    }

    @Override
    public String getName() {
        return "차감";
    }

    @Override
    public String getDescription() {
        return "해당 플레이어에게 경고를 차감합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 차감 <플레이어> <횟수> <사유>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (CommandUtil.argsCheck(3, args.length, player)) {
            return;
        }
        if (CommandUtil.playerExistCheck(args[1], player)) {
            return;
        }
        if (CommandUtil.intCheck(args[2], player)) {
            return;
        }

        OfflinePlayer target = PlayerUtil.getOfflinePlayer(args[1]);
        int count = Integer.parseInt(args[2]);
        String reason = args.length >= 4 ? Util.getFullCommand(args, 3) : "없음";

        UserManager.thenAcceptAsync(target.getUniqueId(), user -> {
            int futureCount = user.getWarningManger().getCount() - count;
            if (futureCount < 0) {
                Info.sendMessage(player, "최소 경고횟수(0회) 미만으로는 차감될 수는 없습니다.");
                return;
            }

            // 메세지 먼저출력 (처벌 메세지는 나중에 출력하기 위함)
            Info.sendMessage(player, target.getName() + "님의 경고 &e" + count + "회&f를 차감했습니다.");
            Info.sendMessage(target, Message.DECREASE.getMessageList("warning_decrease_receive")
                    .replace("%count%", String.valueOf(count))
                    .replace("%reason%", reason)
                    .get());

            if (Config.WARNING_BROADCAST.getBoolean("decrease")) {
                Info.broadMessage(Message.DECREASE.getMessageList("warning_decrease_broadcast")
                        .replace("%player_name%", target.getName())
                        .replace("%count%", String.valueOf(count))
                        .replace("%reason%", reason)
                        .get());
            }

            for (int i = 0; i < count; i++) {
                WarningHandler.decrease(player, user, reason);
            }
        });
    }
}
