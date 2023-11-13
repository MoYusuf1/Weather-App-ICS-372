package edu.metrostate.model.weather;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class FiveDayForecast {

    public static final FiveDayForecast UNKNOWN =
            new FiveDayForecast(Collections.nCopies(5, DailyForecast.UNKNOWN));

    private DailyForecast day1;
    private DailyForecast day2;
    private DailyForecast day3;
    private DailyForecast day4;
    private DailyForecast day5;

    public FiveDayForecast() {
        // default constructor
    }

    public FiveDayForecast(List<DailyForecast> dailyForecasts) {
        if (dailyForecasts == null || dailyForecasts.size() != 5) {
            return;
        }
        this.day1 = dailyForecasts.get(0);
        this.day2 = dailyForecasts.get(1);
        this.day3 = dailyForecasts.get(2);
        this.day4 = dailyForecasts.get(3);
        this.day5 = dailyForecasts.get(4);
    }

    public DailyForecast getDay1() {
        return day1;
    }

    public FiveDayForecast setDay1(DailyForecast day1) {
        this.day1 = day1;
        return this;
    }

    public DailyForecast getDay2() {
        return day2;
    }

    public FiveDayForecast setDay2(DailyForecast day2) {
        this.day2 = day2;
        return this;
    }

    public DailyForecast getDay3() {
        return day3;
    }

    public FiveDayForecast setDay3(DailyForecast day3) {
        this.day3 = day3;
        return this;
    }

    public DailyForecast getDay4() {
        return day4;
    }

    public FiveDayForecast setDay4(DailyForecast day4) {
        this.day4 = day4;
        return this;
    }

    public DailyForecast getDay5() {
        return day5;
    }

    public FiveDayForecast setDay5(DailyForecast day5) {
        this.day5 = day5;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FiveDayForecast.class.getSimpleName() + "[", "]")
                .add("day1=" + day1)
                .add("day2=" + day2)
                .add("day3=" + day3)
                .add("day4=" + day4)
                .add("day5=" + day5)
                .toString();
    }
}
