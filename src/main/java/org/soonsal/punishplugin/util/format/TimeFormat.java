package org.soonsal.punishplugin.util.format;

public class TimeFormat {
    public static String of(int second) {
        String format = "<$h><$m><$s>";

        final int h = second / 60 / 60;
        final int m = second / 60 % 60;
        final int s = second % 60;

        return format
                .replace("<$h>", h > 0 ? h + "시간 " : "")
                .replace("<$m>", m > 0 ? m + "분 " : "")
                .replace("<$s>", s > 0 ? s + "초" : "")
                .trim();
    }

    public static String of(long millsSecond) {
        return of((int) millsSecond / 1000);
    }
}
