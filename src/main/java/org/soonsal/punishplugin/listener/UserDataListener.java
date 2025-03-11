package org.soonsal.punishplugin.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.user.UserManager;
import org.soonsal.punishplugin.util.Info;

import java.util.UUID;

public class UserDataListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        UUID playerUUID = e.getUniqueId();

        try {
            PunishPlugin.getInstance()
                    .getDataManager()
                    .getUserHub()
                    .load(playerUUID.toString());
        } catch (Exception ex) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Info.dataLoadFailMessage());
            throw new RuntimeException(ex);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        User user = UserManager.get(player);

        Bukkit.getScheduler().runTaskAsynchronously(PunishPlugin.getInstance(), () -> {
            PunishPlugin.getInstance()
                    .getDataManager()
                    .getUserHub()
                    .save(user);
            UserManager.remove(player.getUniqueId());
        });
    }
}
