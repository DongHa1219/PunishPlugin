package org.soonsal.punishplugin.punish;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class PunishManager {
    private final Set<Punish> punishes = new HashSet<>();

    public String getPunishListByString(String def) {
        if (punishes.isEmpty()) {
            return def;
        }

        return punishes.stream()
                .map(p -> p.getType().getName())
                .collect(Collectors.joining(","));
    }

    public void clear() {
        for (Punish punish : punishes) {
            punish.stop();
        }

        punishes.clear();
    }

    public Punish getPunishFromCount(int count) {
        return punishes.stream()
                .filter(p -> p.getWarningCount() == count)
                .findFirst()
                .orElse(null);
    }

    public void addPunish(Punish punish) {
        punishes.add(punish);
    }

    public void removePunish(Punish punish) {
        punishes.remove(punish);
    }

    public Set<Punish> getPunishes() {
        return punishes;
    }
}
