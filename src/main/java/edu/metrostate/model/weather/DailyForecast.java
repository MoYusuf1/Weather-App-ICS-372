package edu.metrostate.model.weather;

import edu.metrostate.model.units.TemperatureUnit;

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

    public double convertTemperature(double temperature, TemperatureUnit unit) {
        switch (unit) {
            case CELSIUS:
                return (temperature - 32) * 5.0 / 9.0;
            case KELVIN:
                return (temperature - 32) * 5.0 / 9.0 + 273.15;
            case FAHRENHEIT:
            default:
                return temperature;
        }
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
