package org.soonsal.punishplugin.punish;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.user.UserManager;

import java.util.HashSet;

public class PunishListener implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.get(player);
        PunishManager punishManager = user.getPunishManager();

        for (Punish punish : new HashSet<>(punishManager.getPunishes())) {
            punish.onChat(e);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.get(player);
        PunishManager punishManager = user.getPunishManager();

        for (Punish punish : new HashSet<>(punishManager.getPunishes())) {
            punish.onJoin(e);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.get(player);
        PunishManager punishManager = user.getPunishManager();

        for (Punish punish : new HashSet<>(punishManager.getPunishes())) {
            punish.onMove(e);
        }
    }
}
