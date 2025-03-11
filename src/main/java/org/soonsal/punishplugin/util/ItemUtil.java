package org.soonsal.punishplugin.util;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class ItemUtil {
    public static ItemStack craft(@NotNull Material material, int amount, @Nullable String displayName, @Nullable List<String> lore, int customModelData) {
        ItemStack itemStack = new ItemStack(material, amount);
        itemStack.editMeta(meta -> {
            if (displayName != null) {
                meta.setDisplayName(Util.color(displayName));
            }
            if (lore != null) {
                meta.setLore(Util.color(lore));
            }
            if (customModelData != 0) {
                meta.setCustomModelData(customModelData);
            }
        });

        return itemStack;
    }

    public static ItemStack craftPlayerHead(OfflinePlayer offlinePlayer, String displayName, @Nullable List<String> lore) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();

        meta.setOwningPlayer(offlinePlayer);
        meta.setDisplayName(Util.color(displayName));
        if (lore != null) {
            meta.setLore(Util.color(lore));
        }
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack craft(@NotNull Material material, int amount, @Nullable String displayName) {
        return craft(material, amount, displayName, null, 0);
    }

    public static ItemStack craft(@NotNull Material material, int amount, @Nullable String displayName, @Nullable List<String> lore) {
        return craft(material, amount, displayName, lore, 0);
    }

    public static ItemStack craft(@NotNull Material material, int amount, @Nullable String displayName, int customModelData) {
        return craft(material, amount, displayName, null, customModelData);
    }

    public static ItemStack craft(@NotNull Material material, int amount, @Nullable List<String> lore, int customModelData) {
        return craft(material, amount, null, lore, customModelData);
    }

    public static ItemStack craft(@NotNull Material material, int amount, int customModelData) {
        return craft(material, amount, null, null, customModelData);
    }

    public static ItemStack craft(@NotNull Material material) {
        return craft(material, 1, null, null, 0);
    }
}
