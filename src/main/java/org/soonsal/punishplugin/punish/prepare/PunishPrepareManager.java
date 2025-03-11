package org.soonsal.punishplugin.punish.prepare;

import java.util.*;

public class PunishPrepareManager {
    private static final Map<Integer, PunishPrepare> prepareMap = new HashMap<>();

    public static void add(int count, PunishPrepare prepare) {
        prepareMap.put(count, prepare);
    }

    public static void remove(int count) {
        prepareMap.remove(count);
    }

    public static boolean has(int count) {
        return prepareMap.containsKey(count);
    }

    public static PunishPrepare get(int count) {
        return prepareMap.get(count);
    }

    public static Collection<PunishPrepare> getPunishPrepares() {
        return prepareMap.values();
    }

    public static List<PunishPrepare> getPunishPreparesBySort() {
        return prepareMap.values().stream()
                .sorted(Comparator.comparingInt(PunishPrepare::getCount))
                .toList();
    }
}
