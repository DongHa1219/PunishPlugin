package org.soonsal.punishplugin.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerUtil {
    public static Entity getTargetEntity(Player player, double range) {
        Location eyeLocation = player.getEyeLocation();
        Vector direction = eyeLocation.getDirection();

        // 플레이어의 시선 방향으로 range 만큼 레이캐스팅
        List<Entity> nearbyEntities = player.getNearbyEntities(range, range, range);

        Entity target = null;
        double closestDistanceSquared = Double.MAX_VALUE;

        for (Entity entity : nearbyEntities) {
            if (entity.equals(player)) {
                continue; // 플레이어 자신은 제외
            }

            Location entityLocation = entity.getLocation();
            Vector toEntity = entityLocation.toVector().subtract(eyeLocation.toVector());
            double dotProduct = direction.dot(toEntity.normalize());

            if (dotProduct > 0.99) { // 0.99는 시선과 엔티티 간의 각도를 고려한 임계값입니다.
                double distanceSquared = eyeLocation.distanceSquared(entityLocation);
                if (distanceSquared < closestDistanceSquared) {
                    closestDistanceSquared = distanceSquared;
                    target = entity;
                }
            }
        }

        return target;
    }

    public static void findItemFromPlayer(Player player, ItemStack item, Consumer<ItemStack> consumer) {
        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType().isAir()) {
                continue;
            }

            if (itemStack.isSimilar(item)) {
                consumer.accept(itemStack);
            }
        }
    }

    public static int findItemAmountFromPlayer(Player player, ItemStack item) {
        int amount = 0;

        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null || itemStack.getType().isAir()) {
                continue;
            }

            if (itemStack.isSimilar(item)) {
                amount += itemStack.getAmount();
            }
        }

        return amount;
    }

    public static OfflinePlayer getOfflinePlayer(UUID playerUUID) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null) {
            return player;
        }

        return Bukkit.getOfflinePlayer(playerUUID);
    }

    public static OfflinePlayer getOfflinePlayer(String playerName) {
        if (isOnline(playerName)) {
            return Bukkit.getPlayer(playerName);
        } else {
            return Bukkit.getOfflinePlayerIfCached(playerName);
        }
    }

    public static String getPlayerName(UUID uuid) {
        return getOfflinePlayer(uuid).getName();
    }

    public static boolean isOnline(UUID uuid) {
        return Bukkit.getPlayer(uuid) != null;
    }

    public static boolean isOnline(String name) {
        return Bukkit.getPlayer(name) != null;
    }

    public static boolean hasInventorySlot(Inventory inventory, int requireSlot) {
        return Util.hasInventorySlot(inventory, requireSlot);
    }

    public static boolean hasInventorySlot(Player player, int requireSlot) {
        return hasInventorySlot(player.getInventory(), requireSlot);
    }

    public static boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }
}
