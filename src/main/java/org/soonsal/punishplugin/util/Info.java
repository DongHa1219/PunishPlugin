package org.soonsal.punishplugin.util;

import com.sun.jdi.NativeMethodException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.user.User;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("deprecation")
public class Info {
    private Info() {
    }

    public static void playSound(SoundHolder soundHolder) {
        for (World world : Bukkit.getWorlds()) {
            for (Player player : world.getPlayers()) {
                playSound(player, soundHolder);
            }
        }
    }

    public static void playSound(Player player, SoundHolder soundHolder) {
        player.playSound(player, soundHolder.getSound(), soundHolder.getVolume(), soundHolder.getPitch());
    }

    public static void playSound(Player player, Sound sound, float pitch, float volume) {
        player.playSound(player.getLocation(), sound, pitch, volume);
    }

    public static void playSound(Player player, Sound sound, float pitch) {
        player.playSound(player.getLocation(), sound, pitch, 1);
    }

    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void console(String message) {
        PunishPlugin.getInstance().getLogger().info(message);
    }

    public static void showSubTitle(Player player, @Nullable String subTitle) {
        showTitle(player, " ", subTitle);
    }

    public static void showTitle(Player player, @NotNull String title) {
        showTitle(player, title, null);
    }

    @SuppressWarnings("deprecation")
    public static void showTitle(Player player, @NotNull String title, @Nullable String subTitle) {
        player.sendTitle(title, subTitle, 10, 70, 20);
    }

    public static void warningConsole(String msg) {
        Bukkit.getLogger().warning("[" + PunishPlugin.getInstance().getName() + "] " + msg);
    }

    public static void sendMessage(User user, List<String> messages) {
        for (String message : messages) {
            sendMessage(user, message);
        }
    }

    public static void sendMessage(UUID playerUUID, String message) {
        final Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            sendMessage(player, message);
        }
    }

    @SuppressWarnings("all")
    public static void sendMessage(User user, String message) {
        if (user.isOnline()) {
            sendMessage(user.getPlayer(), message);
        }
    }

    public static void sendActionBar(Player player, String message) {
        player.sendActionBar(Util.color(message));
    }


    public static void sendMessage(OfflinePlayer offlinePlayer, String message) {
        if (offlinePlayer.isOnline()) {
            sendMessage(offlinePlayer.getUniqueId(), message);
        }
    }

    public static void sendMessage(OfflinePlayer offlinePlayer, List<String> messages) {
        for (String message : messages) {
            sendMessage(offlinePlayer, message);
        }
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(Util.color("&e&l[시스템] &f" + message));
    }

    public static void sendMessage(Player player, Component component) {
        player.sendMessage(component);
    }

    public static void sendMessage(Player player, List<String> messages) {
        for (String message : messages) {
            sendMessage(player, message);
        }
    }

    public static void broadMessage(String message) {
        Bukkit.broadcastMessage(Util.color(message));
    }

    public static void broadMessage(List<String> messages) {
        for (String message : messages) {
            broadMessage(message);
        }
    }

    public static void deBugBroadCast(String message) {
        broadMessage(message);
    }

    // Hard Cording ====

    public static Component dataLoadFailMessage() {
        return Component.text("유저 데이터를 불러오는중 오류가 발생했습니다.\n\n").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)
                .append(
                        Component.text("데이터 손실을 막기위해 서버 접속이 거부됩니다. \n")
                                .color(NamedTextColor.WHITE)
                )
                .append(
                        Component.text("관리자에게 문의해주세요.")
                                .color(NamedTextColor.WHITE)
                );
    }

}
