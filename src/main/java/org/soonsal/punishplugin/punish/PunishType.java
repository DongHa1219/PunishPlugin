package org.soonsal.punishplugin.punish;

import org.soonsal.punishplugin.punish.list.Ban;
import org.soonsal.punishplugin.punish.list.Blind;
import org.soonsal.punishplugin.punish.list.Kick;
import org.soonsal.punishplugin.punish.list.Mute;

public enum PunishType {
    NONE(null, "없음"),
    MUTE(Mute.class, "채팅정지"),
    KICK(Kick.class, "일시정지"),
    BAN(Ban.class, "영구정지"),
    BLIND(Blind.class, "블라인드"),
    ;

    private final Class<? extends Punish> punishClass;
    private final String name;

    PunishType(Class<? extends Punish> punishClass, String name) {
        this.punishClass = punishClass;
        this.name = name;
    }

    public boolean isPermanent() {
        if (punishClass == null) {
            return false;
        }

        return Permanent.class.isAssignableFrom(punishClass);
    }

    public String getName() {
        return name;
    }

    public Class<? extends Punish> getPunishClass() {
        return punishClass;
    }
}
