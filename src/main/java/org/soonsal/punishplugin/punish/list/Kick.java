package org.soonsal.punishplugin.punish.list;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.util.PlayerUtil;
import org.soonsal.punishplugin.util.format.DateFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Kick extends Punish {
    public Kick(User user, int warningCount, int duration, long start) {
        super(user, warningCount, duration, start);
    }

    @Override
    public PunishType getType() {
        return PunishType.KICK;
    }

    @SuppressWarnings("all")
    @Override
    public void run() {
        User user = getUser();

        if (user.isOnline()) {
            Player player = user.getPlayer();
            String message = getKickMessage();

            player.kickPlayer(message);
        }

        Bukkit.getBanList(BanList.Type.NAME).addBan(PlayerUtil.getPlayerName(user.getUniqueID()), "경고 누적", getEndDate(), null);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void stop() {
        Bukkit.getBanList(BanList.Type.NAME).pardon(PlayerUtil.getPlayerName(getUser().getUniqueID()));
    }

    @Override
    public void onJoin(PlayerJoinEvent e) {
        remove();
    }

    private String getKickMessage() {
        return String.join(
                "\n",
                Message.PUNISH.getMessageList("kick_message")
                        .replace("%date%", DateFormat.of(getEndDate()))
                        .get()
        );
    }
}
