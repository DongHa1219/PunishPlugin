package org.soonsal.punishplugin.gui.list;

import de.rapha149.signgui.SignGUI;
import de.rapha149.signgui.SignGUIAction;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.soonsal.punishplugin.PunishPlugin;
import org.soonsal.punishplugin.gui.GUIBase;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.punish.prepare.PunishPrepare;
import org.soonsal.punishplugin.punish.prepare.PunishPrepareManager;
import org.soonsal.punishplugin.util.Info;
import org.soonsal.punishplugin.util.ItemUtil;
import org.soonsal.punishplugin.util.Util;
import org.soonsal.punishplugin.util.format.TimeFormat;
import org.soonsal.punishplugin.warning.Warning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PunishSettingGUI extends GUIBase {
    public static final int[] BLACK_GLASS_SLOT = {0, 1, 2, 3, 4, 5, 6, 7, 8, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    public static final int[] WHITE_GLASS_SLOT = {9, 11, 12, 13, 14, 15, 16, 17, 18, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35};
    public static int TARGET_WARNING_ITEM_SLOT = 10;
    public static int PUNISH_SETTING_ITEM_SLOT = 19;
    public static int DURATION_SETTING_ITEM_SLOT = 28;
    public static int APPLY_ITEM_SLOT = 22;
    public static int PUNISH_LIST_ITEM_SLOT = 25;

    // target warning
    private int targetWarning = 1;

    // punish
    private final List<PunishType> typeList = Arrays.stream(PunishType.values())
            .filter(p -> p != PunishType.NONE)
            .toList();
    private int index = 0;

    // duration
    private int duration = 1;

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);

        Player player = (Player) e.getWhoClicked();
        int slot = e.getSlot();

        if (slot == TARGET_WARNING_ITEM_SLOT) {
            // 목표 경고 설정
            if (e.getClick().isRightClick()) {
                // 증가
                if (targetWarning + 1 > Warning.MAX_WARNING) {
                    return;
                }

                targetWarning++;
            } else if (e.getClick().isLeftClick()) {
                // 감소
                if (targetWarning - 1 <= 0) {
                    return;
                }

                targetWarning--;
            }
        } else if (slot == PUNISH_SETTING_ITEM_SLOT) {
           // 처벌 설정
            if (e.getClick().isRightClick()) {
                // 오른쪽으로 이동
                if (index + 1 >= typeList.size()) {
                    index = 0;
                } else {
                    index++;
                }
            } else if (e.getClick().isLeftClick()) {
                // 왼쪽으로 이동
                if (index - 1 < 0) {
                    index = typeList.size() - 1;
                } else {
                    index--;
                }
            }
        } else if (slot == DURATION_SETTING_ITEM_SLOT) {
            // 지속시간 설정
            if (getCurrentPunishType().isPermanent()) {
                return;
            }

            try {
                SignGUI signGUI = SignGUI.builder()
                        .setLine(1, "첫번째줄에")
                        .setLine(2,  "시간(초)을 입력해주세요.")
                        .setLine(3, "----------")
                        .setHandler((p, result) -> {
                            String value = result.getLine(0);
                            if (!Util.isInt(value)) {
                                return List.of(
                                        SignGUIAction.run(() -> Info.sendMessage(p, "숫자를 입력해야합니다."))
                                );
                            }

                            int duration = Integer.parseInt(value);
                            if (duration <= 0) {
                                return List.of(
                                        SignGUIAction.run(() -> Info.sendMessage(p, "1초 이상을 입력해야합니다."))
                                );
                            }

                            this.duration = duration;
                            repaint();
                            return List.of(
                                    SignGUIAction.openInventory(PunishPlugin.getInstance(), getInventory())
                            );
                        })
                        .build();
                signGUI.open(player);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (slot == APPLY_ITEM_SLOT) {
            // 적용하기
            new PunishPrepare(getCurrentPunishType(), targetWarning, getCurrentPunishType().isPermanent() ? -1 : duration);
        } else {
            return;
        }

        Info.playSound(player, Sound.UI_BUTTON_CLICK);
        repaint();
    }

    @Override
    public void setting() {
        for (int slot : BLACK_GLASS_SLOT) {
            place(slot, ItemUtil.craft(Material.BLACK_STAINED_GLASS_PANE));
        }
        for (int slot : WHITE_GLASS_SLOT) {
            place(slot, ItemUtil.craft(Material.WHITE_STAINED_GLASS_PANE));
        }

        place(TARGET_WARNING_ITEM_SLOT, getTargetWarningItem());
        place(PUNISH_SETTING_ITEM_SLOT, getPunishSettingItem());
        place(DURATION_SETTING_ITEM_SLOT, getDurationSettingItem());
        place(APPLY_ITEM_SLOT, getApplyItem());
        place(PUNISH_LIST_ITEM_SLOT, getPunishListItem());
    }

    private ItemStack getPunishListItem() {
        List<String> lore = new ArrayList<>();

        for (PunishPrepare prepare : PunishPrepareManager.getPunishPreparesBySort()) {
            lore.add("&a경고 " + prepare.getCount() + "회 &f-> " + prepare.getType().getName() + (prepare.getType().isPermanent() ? "" : "&8&o(" + TimeFormat.of(prepare.getDuration()) + "동안)"));
        }

        if (lore.isEmpty()) {
            lore.add("&f설정된 처벌이 없습니다.");
        }

        return ItemUtil.craft(
                Material.NAME_TAG,
                1,
                "&e&l처벌 목록",
                lore
        );
    }

    private ItemStack getApplyItem() {
        return ItemUtil.craft(
                Material.BELL,
                1,
                "&e&l적용하기",
                List.of(
                        "",
                        " &f 경고 &e" + targetWarning + "회&f에 도달하면 &e" + getCurrentPunishType().getName() + "&f가 적용됩니다. ",
                        ""
                )
        );
    }

    private ItemStack getDurationSettingItem() {
        List<String> lore = new ArrayList<>();

        lore.add("");
        lore.add(" &f설정된 지속시간: " + (getCurrentPunishType().isPermanent() ? "영구" : duration + "초&8&o(" + TimeFormat.of(duration) + ")"));
        if (!getCurrentPunishType().isPermanent()) {
            lore.add("   &8ㄴ 클릭시 입력창으로 이동합니다.");
            lore.add("   &8ㄴ 첫번째줄에 시간(초)을 입력해주세요.");
        }
        lore.add("");

        ItemStack item = ItemUtil.craft(
                Material.CLOCK,
                1,
                "&e&l지속시간 설정",
                lore
        );

        item.editMeta(meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        });

        return item;
    }

    private ItemStack getPunishSettingItem() {
        PunishType currentType = typeList.get(index);
        PunishType previousType = typeList.get((index - 1) < 0 ? (typeList.size() - 1) : (index - 1));
        PunishType nextType = typeList.get((index + 1) >= typeList.size() ? 0 : (index + 1));

        return ItemUtil.craft(
                Material.WRITABLE_BOOK,
                1,
                "&e&l처벌 설정",
                List.of(
                        "",
                        " &8" + previousType.getName() + " &f<- &e&l" + currentType.getName() + " &f-> &8" + nextType.getName() + " ",
                        ""
                )
        );
    }

    private ItemStack getTargetWarningItem() {
        return ItemUtil.craft(
                Material.ACACIA_HANGING_SIGN,
                1,
                "&e&l목표 경고 설정",
                List.of(
                        "",
                        " &f목표 경고: " + targetWarning + "회",
                        "  &8ㄴ 우클릭: +1 / 좌클릭: -1",
                        ""
                )
        );
    }

    @Override
    public String getTitle() {
        return "처벌 설정";
    }

    @Override
    public int getInventorySize() {
        return 45;
    }

    private void clearParameter() {
        this.duration = 0;
    }

    private PunishType getCurrentPunishType() {
        return typeList.get(index);
    }
}
