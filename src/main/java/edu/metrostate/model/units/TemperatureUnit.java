package edu.metrostate.model.units;

// https://www.nist.gov/pml/owm/si-units-temperature
public enum TemperatureUnit {
    FAHRENHEIT("Fahrenheit", "F"), // imperial
    CELSIUS("Celsius", "C"), // metric
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

    public String convertAndDisplay(String label, double originalValue) {
        double newValue = originalValue;
        if (this == CELSIUS) {
            newValue = (originalValue - 32) * 5.0 / 9.0;
        } else if (this == KELVIN) {
            newValue = (originalValue - 32) * 5.0 / 9.0 + 273.15;
        }
        return String.format("%s: %.1f\u00B0%s", label, newValue, this.suffix);
    }
}
