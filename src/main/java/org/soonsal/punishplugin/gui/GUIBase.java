package org.soonsal.punishplugin.gui;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.PunishPlugin;

public abstract class GUIBase implements InventoryHolder, InventoryGraphic {
    private final Inventory inventory;

    public GUIBase() {
        inventory = Bukkit.createInventory(this, getInventorySize(), LegacyComponentSerializer
                .legacySection().deserialize(getTitle()));
    }

    public GUIBase(String title, int size) {
        inventory = Bukkit.createInventory(this, size, LegacyComponentSerializer
                .legacySection().deserialize(title));
    }

    public abstract String getTitle();

    public abstract int getInventorySize();

    public abstract void setting();

    public void open(Player player) {
        setting();
        player.openInventory(inventory);
    }

    public void openSync(Player player) {
        Bukkit.getScheduler().runTask(PunishPlugin.getInstance(), () -> open(player));
    }

    public void place(int slot, @Nullable ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    public void place(@NotNull ItemStack item) {
        inventory.addItem(item);
    }

    public void onClick(InventoryClickEvent e) {
        // default
    }

    public void onClose(InventoryCloseEvent e) {
        // default
    }

    public void onDrag(InventoryDragEvent e) {
        // default
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void repaint() {
        clear();
        setting();
    }

    @Override
    public void clear() {
        for (int i = 0; i < getInventorySize(); i++) {
            place(i, null);
        }
    }
}
