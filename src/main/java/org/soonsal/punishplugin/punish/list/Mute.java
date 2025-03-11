package org.soonsal.punishplugin.punish.list;

import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.format.DateFormat;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Mute extends Punish {
    public Mute(User user, int warningCount, int duration, long start) {
        super(user, warningCount, duration, start);
    }

    @Override
    public PunishType getType() {
        return PunishType.MUTE;
    }

    @Override
    public void run() {
        User user = getUser();
        Info.sendMessage(user, getMuteMessage());
    }

    @Override
    public void stop() {}

    @SuppressWarnings("deprecation")
    @Override
    public void onChat(AsyncPlayerChatEvent e) {
        if (isEnd()) {
            remove();
            return;
        }

        Info.sendMessage(e.getPlayer(), getMuteMessage());
        e.setCancelled(true);
    }

    private List<String> getMuteMessage() {
        return Message.PUNISH.getMessageList("mute_message")
                .replace("%date%", DateFormat.of(getEndDate()))
                .get();
    }
}
