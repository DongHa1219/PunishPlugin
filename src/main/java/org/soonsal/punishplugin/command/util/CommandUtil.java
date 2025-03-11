package org.soonsal.punishplugin.command.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.PlayerUtil;
import org.soonsal.punishplugin.util.Util;

public class CommandUtil {
    public static boolean intCheck(String word, Player target) {
        if (!Util.isInt(word)) {
            Info.sendMessage(target, "숫자를 입력해주세요. 잘못입력된 부분: " + word);
            return true;
        }

        return false;
    }

    public static boolean argsCheck(int require_argLength, int input_argLength, Player target) {
        if (input_argLength < require_argLength) {
            Info.sendMessage(target, "명령어를 제대로 입력해주세요.");
            return true;
        }

        return false;
    }

    public static boolean playerExistCheck(String playerName, Player target) {
        OfflinePlayer offlinePlayer = PlayerUtil.getOfflinePlayer(playerName);

        if (offlinePlayer == null) {
            Info.sendMessage(target, "해당 플레이어는 존재하지 않습니다.");
            return true;
        }

        return false;
    }

    public static boolean playerOnlineCheck(String playerName, Player target) {
        if (!PlayerUtil.isOnline(playerName)) {
            Info.sendMessage(target, "해당 플레이어는 오프라인 상태입니다.");
            return true;
        }

        return false;
    }
}
