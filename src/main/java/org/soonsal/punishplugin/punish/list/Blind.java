package org.soonsal.punishplugin.punish.list;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.config.list.Config;
import org.soonsal.punishplugin.config.list.Message;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.format.DateFormat;

public class Blind extends Punish {
    public Blind(User user, int warningCount, int duration, long start) {
        super(user, warningCount, duration, start);
    }

    @Override
    public PunishType getType() {
        return PunishType.BLIND;
    }

    @Override
    public void run() {
        Player player = getUser().getPlayer();

        if (player != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, getRemainingTime() * 20, 2));
        }

        User user = getUser();
        final int DELAY = Config.BLIND_CHAT_PERIOD.getInt();
        final int DELAY_AS_TICK = DELAY * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isEnd() || !user.isOnline()) {
                    cancel();
                    return;
                }

                Info.sendMessage(user, Message.PUNISH.getMessageList("blind_message")
                        .replace("%date%", DateFormat.of(getEndDate()))
                        .get());
            }
        }.runTaskTimerAsynchronously(PunishPlugin.getInstance(), 0, DELAY_AS_TICK);
    }

    @Override
    public void onJoin(PlayerJoinEvent e) {
        if (isEnd()) {
            remove();
            return;
        }

        run();
    }

    @Override
    public void onMove(PlayerMoveEvent e) {
        if (isEnd()) {
            remove();
            return;
        }

        e.setCancelled(true);
    }

    @Override
    public void stop() {
        Player player = getUser().getPlayer();

        if (player != null) {
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }
    }
}
