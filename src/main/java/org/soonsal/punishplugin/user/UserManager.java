package org.soonsal.punishplugin.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.data.hubs.Hub;
import org.soonsal.punishplugin.util.Util;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class UserManager {
    private static final ConcurrentHashMap<UUID, User> userMap = new ConcurrentHashMap<>();

    public static Collection<User> getUsers() {
        return userMap.values();
    }

    public static void add(UUID uuid, User user) {
        userMap.put(uuid, user);
    }

    public static void remove(UUID uuid) {
        userMap.remove(uuid);
    }

    public static boolean contain(UUID uuid) {
        return userMap.containsKey(uuid);
    }

    @Nullable
    public static User get(UUID uniqueID) {
        return userMap.get(uniqueID);
    }

    @NotNull
    public static User get(Player player) {
        return userMap.get(player.getUniqueId());
    }

    public static void thenAccept(UUID uniqueID, Consumer<User> consumer) {
        Hub<User> userHub = PunishPlugin.getInstance().getDataManager().getUserHub();
        User user = userMap.containsKey(uniqueID)
                ? userMap.get(uniqueID)
                : userHub.load(uniqueID.toString());

        consumer.accept(user);

        if (!Util.isOnline(user.getUniqueID())) {
            userHub.save(user);
            userMap.remove(user.getUniqueID());
        }
    }

    public static void thenAcceptAsync(UUID uniqueID, Consumer<User> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(PunishPlugin.getInstance(), () -> {
           thenAccept(uniqueID, consumer);
        });
    }
}
