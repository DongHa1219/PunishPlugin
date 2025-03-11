package org.soonsal.punishplugin.gui.list;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.soonsal.punishplugin.config.list.GUI;
import org.soonsal.punishplugin.gui.GUIBase;
import org.soonsal.punishplugin.user.User;
import org.soonsal.punishplugin.util.PlayerUtil;
import org.soonsal.punishplugin.util.StringReplacer;
import org.soonsal.punishplugin.util.format.DateFormat;
import org.soonsal.punishplugin.util.page.Page;
import org.soonsal.punishplugin.util.page.PageController;
import org.soonsal.punishplugin.util.page.PageManager;
import org.soonsal.punishplugin.warning.Warning;
import org.soonsal.punishplugin.warning.WarningType;

public class WarningListGUI extends GUIBase {
    private static final int[] GLASS_SLOT = {
            46, 47, 48, 50, 51, 52
    };
    private static final int PREVIOUS_BUTTON = 45;
    private static final int NEXT_BUTTON = 53;
    private static final int WARNING_INFO_ITEM_SLOT = 49;
    private static final int[] WARNING_SLOT = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
    };
    private final User user;
    private final PageController<Warning> controller;

    public WarningListGUI(User user) {
        this.user = user;
        this.controller = new PageController<>(
                new PageManager<>(user.getWarningManger().getWarnings(), WARNING_SLOT.length),
                1,
                this
        );
    }

    @Override
    public void setting() {
        place(PREVIOUS_BUTTON, GUI.WARNING_LIST_GUI.getItem("items.previous_button"));
        place(NEXT_BUTTON, GUI.WARNING_LIST_GUI.getItem("items.next_button"));
        place(WARNING_INFO_ITEM_SLOT, getWarningInfoItem());
        ItemStack glassItem = GUI.WARNING_LIST_GUI.getItem("items.decoration_item");
        for (int slot : GLASS_SLOT) {
            place(slot, glassItem);
        }

        int index = 0;
        Page<Warning> page = controller.getPage();
        for (Warning warning : page.getElements()) {
            int slot = WARNING_SLOT[index++];

            place(slot, getWarningItem(warning));
        }
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);

        int slot = e.getRawSlot();
        if (slot == NEXT_BUTTON) {
            if (!controller.hasNextPage()) {
                return;
            }

            controller.pressNextButton();
        } else if (slot == PREVIOUS_BUTTON) {
            if (!controller.hasPreviousPage()) {
                return;
            }

            controller.pressPreviousButton();
        }
    }

    private ItemStack getWarningInfoItem() {
        ItemStack item = GUI.WARNING_LIST_GUI.getItem("items.warning_info_item");

        item.editMeta(meta -> StringReplacer.newReplacer()
                .append(meta)
                .replace("%count%", String.valueOf(user.getWarningManger().getCount()))
                .replace("%punish_list%", user.getPunishManager().getPunishListByString("없음"))
                .apply(meta));

        return item;
    }

    private ItemStack getWarningItem(Warning warning) {
        ItemStack item = warning.getWarningType() == WarningType.INCREASE
                ? GUI.WARNING_LIST_GUI.getItem("items.warning_increase_item")
                : GUI.WARNING_LIST_GUI.getItem("items.warning_decrease_item");

        item.editMeta(meta -> StringReplacer.newReplacer()
                .append(meta)
                .replace("%id%", String.valueOf(warning.getId()))
                .replace("%count%", String.valueOf(user.getWarningManger().getCountFromWarning(warning)))
                .replace("%player_name%", PlayerUtil.getPlayerName(warning.getFrom()))
                .replace("%date%", DateFormat.of(warning.getDate()))
                .replace("%reason%", warning.getReason())
                .replace("%punish_type%", warning.getPunishType().getName())
                .apply(meta));

        return item;
    }

    @Override
    public String getTitle() {
        return GUI.WARNING_LIST_GUI.getTitle();
    }

    @Override
    public int getInventorySize() {
        return 54;
    }
}
