package org.soonsal.punishplugin.warning;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.punish.prepare.PunishPrepare;
import org.soonsal.punishplugin.punish.prepare.PunishPrepareManager;
import org.soonsal.punishplugin.user.User;

import java.util.Date;

public class WarningHandler {
    public static void increase(Player from, User user, @Nullable String reason) {
        WarningManger warningManger = user.getWarningManger();
        int currentCount = warningManger.getCount();
        int futureCount = currentCount + 1;

        PunishPrepare punishPrepare = PunishPrepareManager.get(futureCount);
        PunishType type = punishPrepare != null ? punishPrepare.getType() : PunishType.NONE;

        WarningBuilder.builder()
                .setWarningType(WarningType.INCREASE)
                .setFrom(from.getUniqueId())
                .setTo(user.getUniqueID())
                .setDate(new Date())
                .setPunishType(type)
                .setReason(reason)
                .withApply(warningManger)
                .build();

        if (punishPrepare != null) {
            Punish punish = punishPrepare.perform(user, futureCount);
            punish.runSync();
        }
    }

    public static void decrease(Player from, User to, @Nullable String reason) {
        WarningManger warningManger = to.getWarningManger();
        int count = warningManger.getCount();
        Punish punish = to.getPunishManager().getPunishFromCount(count);

        if (punish != null) {
            punish.remove();
        }

        WarningBuilder.builder()
                .setWarningType(WarningType.DECREASE)
                .setFrom(from.getUniqueId())
                .setTo(to.getUniqueID())
                .setDate(new Date())
                .setPunishType(PunishType.NONE)
                .setReason(reason)
                .withApply(warningManger)
                .build();
    }
}
