package org.soonsal.punishplugin.punish;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.user.User;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public abstract class Punish {
    private final User user;
    private final int warningCount;
    private final int duration; // -1 : 무제한
    private final long start;

    public Punish(User user, int warningCount, int duration, long start) {
        this.user = user;
        this.warningCount = warningCount;
        this.duration = duration;
        this.start = start;

        user.getPunishManager().addPunish(this);
    }

    public static Punish newPunish(PunishType type, User user, int warningCount, int duration, long start) {
        try {
            return type.getPunishClass()
                    .getDeclaredConstructor(User.class, int.class, int.class, long.class)
                    .newInstance(user, warningCount, duration, start);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Date getEndDate() {
        long future = getStart() + TimeUnit.SECONDS.toMillis(getDuration());
        return new Date(future);
    }

    public void remove() {
        stop();
        getUser().getPunishManager().removePunish(this);
    }

    public int getRemainingTime() {
        return Math.max(duration - getPassTime(), 0);
    }

    public int getPassTime() {
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start);
    }

    public int getDuration() {
        return duration;
    }

    public long getStart() {
        return start;
    }

    public boolean isEnd() {
        if (duration == -1) {
            return false;
        }

        return (System.currentTimeMillis() - start) > TimeUnit.SECONDS.toMillis(duration);
    }

    public boolean isProgress() {
        return !isEnd();
    }

    public User getUser() {
        return user;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public abstract PunishType getType();

    public void runSync() {
        Bukkit.getScheduler().runTask(PunishPlugin.getInstance(), this::run);
    }

    public abstract void run();

    public abstract void stop();

    @SuppressWarnings("deprecation")
    public void onChat(AsyncPlayerChatEvent e) {};

    public void onJoin(PlayerJoinEvent e) {}

    public void onMove(PlayerMoveEvent e) {}
}
