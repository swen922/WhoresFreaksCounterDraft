package com.horovod.android.whoresfreakscounterdraft;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    private static final DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final DateFormat timeFormatter = new SimpleDateFormat("HH:mm");


    public static String formatDate(Date date) {
        if (date == null) {
            date = new Date();
        }
        return dateFormatter.format(date);
    }

    public static String formatTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        return timeFormatter.format(date);
    }

}
