package edu.metrostate.model.weather;

import java.util.Objects;
import java.util.StringJoiner;

public class Weather {

    public static final Weather UNKNOWN = new Weather()
            .setIcon("01d")
            .setWindDirection("Unknown")
            .setDescription("Unknown")
            .setLocationName("Unknown")
            .setDay("Unknown");

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
    private String icon;
    private double dewPoint;
    private double pressure;
    private int uv;
    private String locationName;
    private String day;

    public int getTemperature() {
        return temperature;
    }

    public Weather setTemperature(int temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Weather setDay(String day) {
        this.day = day;
        return this;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    // Need to calculate dewpoint since the API call doesn't provide this
    public static int calculateDewPoint(int temperature, double humidity) {
        humidity = humidity / 100.0;
        double alpha = ((17.27 * (double) temperature) / (237.7 + (double) temperature)) + Math.log(humidity);
        double dewPointFahrenheit = (237.7 * alpha) / (17.27 - alpha);
        return (int) dewPointFahrenheit;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather weather = (Weather) o;
        return Objects.equals(description, weather.description) && Objects.equals(locationName, weather.locationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, locationName);
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
