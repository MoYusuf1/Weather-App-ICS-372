package edu.metrostate.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    private TimeUtils() {
        throw new IllegalAccessError("how rude!");
    }

    public static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DATE_TIME_FORMATTER);
    }

}
