package org.soonsal.punishplugin.warning;

import org.jetbrains.annotations.Nullable;
import org.soonsal.punishplugin.punish.PunishType;

import java.util.Date;
import java.util.UUID;

public class Warning {
    public static int MAX_WARNING = 54;
    private final int id;
    private final WarningType warningType;
    private final UUID from;
    private final UUID to;
    private final Date date;
    private final PunishType punishType;
    private @Nullable String reason;

    public Warning(int id, WarningType warningType, UUID from, UUID to, Date date, PunishType punishType, @Nullable String reason) {
        this.id = id;
        this.warningType = warningType;
        this.from = from;
        this.to = to;
        this.date = date;
        this.punishType = punishType;
        this.reason = reason;
    }

    public Warning(int id, WarningType warningType, UUID from, UUID to, Date date, PunishType punishType) {
        this(id, warningType, from, to, date, punishType,null);
    }

    public int getId() {
        return id;
    }

    public WarningType getWarningType() {
        return warningType;
    }

    public PunishType getPunishType() {
        return punishType;
    }

    public void setReason(@Nullable String reason) {
        this.reason = reason;
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public @Nullable String getReason() {
        return reason;
    }
}
