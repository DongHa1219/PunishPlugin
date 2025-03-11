package org.soonsal.punishplugin.version;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.util.Info;

public class VersionListener implements Listener {
    private boolean check = false;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (player.isOp() && VersionHandler.hasNewVersion()) {
            if (check) {
                return;
            }

            check = true;

            Bukkit.getScheduler().runTaskLater(PunishPlugin.getInstance(), () -> {
                Info.sendMessage(player, "현재 구버전의 처벌플러그인을 사용중입니다.");
            }, 20);
        }
    }
}
