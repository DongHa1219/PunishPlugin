package org.soonsal.punishplugin.punish.list;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.punish.Permanent;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.util.PlayerUtil;

public class Ban extends Punish implements Permanent {
    public Ban(User user, int warningCount, int duration, long start) {
        super(user, warningCount, duration, start);
    }

    @Override
    public PunishType getType() {
        return PunishType.BAN;
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        User user = getUser();

        if (user.isOnline()) {
            Player player = user.getPlayer();
            String message = getBanMessage();

            player.kickPlayer(message);
        }

        Bukkit.getBanList(BanList.Type.NAME).addBan(PlayerUtil.getPlayerName(user.getUniqueID()), "경고 누적", null, null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void stop() {
        Bukkit.getBanList(BanList.Type.NAME).pardon(PlayerUtil.getPlayerName(getUser().getUniqueID()));
    }

    private String getBanMessage() {
        return String.join(
                "\n",
                Message.PUNISH.getMessageList("ban_message").get()
        );
    }

    @Override
    public void onJoin(PlayerJoinEvent e) {
        remove();
    }
}
