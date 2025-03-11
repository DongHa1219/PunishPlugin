package org.soonsal.punishplugin.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public interface InventoryGraphic {
    void repaint();

    void clear();

    default void updateInventory(Inventory inventory, String newTitle) {
        inventory.getViewers().stream()
                .map(v -> (Player) v)
                .forEach(player -> {
                    InventoryView view = player.getOpenInventory();
                    view.setTitle(newTitle);
                });
    }
}
