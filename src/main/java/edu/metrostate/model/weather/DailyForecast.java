package edu.metrostate.model.weather;

import java.util.StringJoiner;

public class DailyForecast {

    public static final DailyForecast UNKNOWN = new DailyForecast()
            .setIcon("01d")
            .setDay("Unknown");

    private double temperatureMin;
    private double temperatureMax;
    private String icon;
    private String day;

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public DailyForecast setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
        return this;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public DailyForecast setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public DailyForecast setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getDay() {
        return day;
    }

    public DailyForecast setDay(String day) {
        this.day = day;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DailyForecast.class.getSimpleName() + "[", "]")
                .add("temperatureMin=" + temperatureMin)
                .add("temperatureMax=" + temperatureMax)
                .add("icon='" + icon + "'")
                .add("day='" + day + "'")
                .toString();
    }
}
