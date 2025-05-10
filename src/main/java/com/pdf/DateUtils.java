package com.pdf;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {
    public static String formatCalendarDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(calendar.getTime());
    }
}
