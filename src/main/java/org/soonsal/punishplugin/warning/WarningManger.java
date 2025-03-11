package org.soonsal.punishplugin.warning;

import java.util.ArrayList;
import java.util.List;

public class WarningManger {
    private final List<Warning> warningList = new ArrayList<>();

    public int getCountFromWarning(Warning warning) {
        int count = 0;

        for (Warning w : warningList) {
            if (w.getWarningType() == WarningType.INCREASE) {
                count++;
            } else if (w.getWarningType() == WarningType.DECREASE) {
                count--;
            }

            if (w.getId() == warning.getId()) {
                break;
            }
        }

        return count;
    }

    public void addWarning(Warning warning) {
        warningList.add(warning);
    }

    public void clear() {
        warningList.clear();
    }

    public int getCount() {
        return warningList.stream()
                .mapToInt(warning -> warning.getWarningType() == WarningType.INCREASE ? 1 : -1)
                .sum();
    }

    public List<Warning> getWarnings() {
        return warningList;
    }
}
