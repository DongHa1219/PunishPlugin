package org.soonsal.punishplugin.warning;

import org.soonsal.punishplugin.punish.PunishType;

import java.util.Date;
import java.util.UUID;

public class WarningBuilder {
    private int id = -1;
    private WarningType warningType;
    private UUID from;
    private UUID to;
    private Date date;
    private PunishType punishType;
    private String reason;
    private WarningManger warningManger;

    public static WarningBuilder builder() {
        return new WarningBuilder();
    }

    public WarningBuilder setID(int id) {
        this.id = id;
        return this;
    }

    public WarningBuilder setWarningType(WarningType type) {
        this.warningType = type;
        return this;
    }

    public WarningBuilder setFrom(UUID from) {
        this.from = from;
        return this;
    }

    public WarningBuilder setTo(UUID to) {
        this.to = to;
        return this;
    }

    public WarningBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public WarningBuilder setPunishType(PunishType punishType) {
        this.punishType = punishType;
        return this;
    }

    public WarningBuilder setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public WarningBuilder withApply(WarningManger warningManger) {
        this.warningManger = warningManger;
        return this;
    }

    public Warning build() {
        if (id == -1) {
            id = WarningIdProvider.getInstance().generate();
        }

        Warning warning = new Warning(id, warningType, from, to, date, punishType, reason);

        if (warningManger != null) {
            warningManger.addWarning(warning);
        }

        return warning;
    }
}
