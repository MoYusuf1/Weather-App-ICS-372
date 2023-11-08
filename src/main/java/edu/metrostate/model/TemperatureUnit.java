package edu.metrostate.model;

// TODO: add conversion from one unit to another
// https://www.nist.gov/pml/owm/si-units-temperature
public enum TemperatureUnit {
    FAHRENHEIT("Fahrenheit", "°F"), // imperial
    CELSIUS("Celsius", "°C"), // metric
    KELVIN("Kelvin", "K");

    private final String description;
    private final String suffix;

    TemperatureUnit(String description, String suffix) {
        this.description = description;
        this.suffix = suffix;
    }

    public String getDescription() {
        return description;
    }

    public String getSuffix() {
        return suffix;
    }
}
