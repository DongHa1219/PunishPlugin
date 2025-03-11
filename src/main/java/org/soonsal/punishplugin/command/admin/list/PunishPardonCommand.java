package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.CommandUtil;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.PlayerUtil;

public class PunishPardonCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.PARDON.getPermission();
    }

    @Override
    public String getName() {
        return "처벌해제";
    }

    @Override
    public String getDescription() {
        return "해당 경고때 받은 처벌을 해제합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 처벌해제 <플레이어> <경고횟수>";
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

        OfflinePlayer offlinePlayer = PlayerUtil.getOfflinePlayer(args[1]);
        int warningCount = Integer.parseInt(args[2]);

        UserManager.thenAcceptAsync(offlinePlayer.getUniqueId(), user -> {
            Punish punish = user.getPunishManager().getPunishFromCount(warningCount);

            if (punish == null) {
                Info.sendMessage(player, "받은 경고가 존재하지 않거나 해당 경고때는 처벌을 받지 않았습니다.");
                return;
            }

            punish.remove();
            Info.sendMessage(player, offlinePlayer.getName() + "님의 " + punish.getType().getName() + "가 해제되었습니다.");
        });
    }
}
