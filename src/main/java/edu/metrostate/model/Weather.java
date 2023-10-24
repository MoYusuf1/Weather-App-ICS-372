package edu.metrostate.model;

public class Weather {
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

    public Weather(int temperature, double temperatureMin, double temperatureMax, double humidity, double windSpeed, String windDirection, int clouds, int sunrise, int sunset, double visibility, String description, String icon, double dewPoint, double pressure, int uv, String locationName) {
        this.temperature = temperature;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.clouds = clouds;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.visibility = visibility;
        this.description = description;
        this.icon = icon;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.uv = uv;
        this.locationName = locationName;
    }

    public int calculateDewPoint() {
        double temperatureFahrenheit = this.temperature;
        double humidity = this.humidity / 100.0;

        double alpha = ((17.27 * temperatureFahrenheit) / (237.7 + temperatureFahrenheit)) + Math.log(humidity);
        double dewPointFahrenheit = (237.7 * alpha) / (17.27 - alpha);

        return (int) dewPointFahrenheit;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(float temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() { return icon; }

    public double getDewPoint() { return dewPoint; }

    public void setDewPoint(double dewPoint) { this.dewPoint = dewPoint; }

    public double getPressure() { return pressure; }

    public int getUV() { return uv; }

    public String getLocationName() { return locationName; }

    public void setLocationName(String locationName) { this.locationName = locationName; }

}
