package org.soonsal.punishplugin.util.format;

import org.soonsal.punishplugin.config.list.Config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static final SimpleDateFormat formatter = new SimpleDateFormat(Config.DATE_FORMAT.getString());

    public static String of(Date date) {
        return formatter.format(date);
    }

    public static Date parse(String data) {
        if (data == null) {
            return null;
        }

        try {
            return formatter.parse(data);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
