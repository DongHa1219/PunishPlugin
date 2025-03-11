package org.soonsal.punishplugin.command.admin.list;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.soonsal.punishplugin.command.CommandPermission;
import org.soonsal.punishplugin.command.util.CommandUtil;
import org.soonsal.punishplugin.command.util.SubCommand;
import org.soonsal.punishplugin.gui.list.WarningListGUI;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.PlayerUtil;

public class PunishHistoryConfirmCommand implements SubCommand {
    @Override
    public String getPermission() {
        return CommandPermission.CONFIRM.getPermission();
    }

    @Override
    public String getName() {
        return "확인";
    }

    @Override
    public String getDescription() {
        return "해당 플레이어의 경고 히스토리를 확인합니다.";
    }

    @Override
    public String getSyntax() {
        return "/경고관리 확인 <플레이어>";
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
            WarningListGUI gui = new WarningListGUI(user);
            gui.openSync(player);
        });
    }
}
