package edu.metrostate.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");
    private static final DateTimeFormatter DATE_TIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE");

    private TimeUtils() {
        throw new IllegalAccessError("How rude!");
    }

    public static String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.format(DATE_TIME_FORMATTER);
    }

    public static String getDayOfWeek(String dtTxt) {
        // Take the date and then assign it to a day of the week format
        // https://www.tutorialspoint.com/formatting-day-of-week-in-eeee-format-in-java
        LocalDateTime dateTime = LocalDateTime.parse(dtTxt, DATE_TIME_INPUT_FORMATTER);
        return dateTime.format(DATE_TIME_OUTPUT_FORMATTER);
    }

}
