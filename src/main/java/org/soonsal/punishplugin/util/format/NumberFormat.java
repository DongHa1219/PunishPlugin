package org.soonsal.punishplugin.util.format;

import java.text.DecimalFormat;

public class NumberFormat {
    private static final DecimalFormat doubleFormat = new DecimalFormat("#.###");
    private static final DecimalFormat intFormat = new DecimalFormat("###,###");

    public static String of(int amount) {
        return intFormat.format(amount);
    }

    public static String of(double amount) {
        return doubleFormat.format(amount);
    }
}
