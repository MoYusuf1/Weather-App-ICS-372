package edu.metrostate.model;

import java.util.StringJoiner;

public class Weather {

    public static final Weather CITY_NOT_FOUND = new Weather();

    private int temperature;
    private double temperatureMin;
    private double temperatureMax;
    private double humidity;
    private double windSpeed;
    private String windDirection;
    private int clouds;
    private int sunrise;
    private int sunset;
    private double visibility;
    private String description;
    private String icon; // Add icon property
    private double dewPoint; // Add dewPoint property
    private double pressure; // Add pressure property
    private int uv; // Add uv property
    private String locationName;

    public int getTemperature() {
        return temperature;
    }

    public Weather setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public Weather setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
        return this;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public Weather setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
        return this;
    }

    public double getHumidity() {
        return humidity;
    }

    public Weather setHumidity(double humidity) {
        this.humidity = humidity;
        return this;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public Weather setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public Weather setWindDirection(String windDirection) {
        this.windDirection = windDirection;
        return this;
    }

    public int getClouds() {
        return clouds;
    }

    public Weather setClouds(int clouds) {
        this.clouds = clouds;
        return this;
    }

    public int getSunrise() {
        return sunrise;
    }

    public Weather setSunrise(int sunrise) {
        this.sunrise = sunrise;
        return this;
    }

    public int getSunset() {
        return sunset;
    }

    public Weather setSunset(int sunset) {
        this.sunset = sunset;
        return this;
    }

    public double getVisibility() {
        return visibility;
    }

    public Weather setVisibility(double visibility) {
        this.visibility = visibility;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Weather setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Weather setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public double getDewPoint() {
        return dewPoint;
    }

    public Weather setDewPoint(double dewPoint) {
        this.dewPoint = dewPoint;
        return this;
    }

    public double getPressure() {
        return pressure;
    }

    public Weather setPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public int getUv() {
        return uv;
    }

    public Weather setUv(int uv) {
        this.uv = uv;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public Weather setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Weather.class.getSimpleName() + "[", "]")
                .add("temperature=" + temperature)
                .add("temperatureMin=" + temperatureMin)
                .add("temperatureMax=" + temperatureMax)
                .add("humidity=" + humidity)
                .add("windSpeed=" + windSpeed)
                .add("windDirection='" + windDirection + "'")
                .add("clouds=" + clouds)
                .add("sunrise=" + sunrise)
                .add("sunset=" + sunset)
                .add("visibility=" + visibility)
                .add("description='" + description + "'")
                .add("icon='" + icon + "'")
                .add("dewPoint=" + dewPoint)
                .add("pressure=" + pressure)
                .add("uv=" + uv)
                .add("locationName='" + locationName + "'")
                .toString();
    }
}
