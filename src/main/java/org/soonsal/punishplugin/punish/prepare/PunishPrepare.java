package org.soonsal.punishplugin.punish.prepare;

import org.soonsal.punishplugin.punish.Punish;
import org.soonsal.punishplugin.punish.PunishType;
import org.soonsal.punishplugin.user.User;

public class PunishPrepare {
    private final PunishType type;
    private final int count;
    private final int duration;

    public PunishPrepare(PunishType type, int count, int duration) {
        this.type = type;
        this.count = count;
        this.duration = duration;

        PunishPrepareManager.add(count, this);
    }

    public Punish perform(User target, int startWarningCount) {
        if (type == PunishType.NONE) {
            throw new IllegalStateException();
        }

        return Punish.newPunish(type, target, startWarningCount, duration, System.currentTimeMillis());
    }

    public int getCount() {
        return count;
    }

    public PunishType getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }
}
