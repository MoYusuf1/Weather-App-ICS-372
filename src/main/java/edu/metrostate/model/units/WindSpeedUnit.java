package edu.metrostate.model.units;

// https://www.weather.gov/epz/wxcalc_windconvert
public enum WindSpeedUnit {
    KNOTS("knots", "knots"),
    MPH("miles per hour", "mph"),
    KPH("kilometers per hour", "km/h"),
    MS("meters per second", "m/s"),
    FTS("feet per second", "ft/s");

    private final String description;
    private final String suffix;

    WindSpeedUnit(String description, String suffix) {
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
        if (this == KPH) {
            newValue = originalValue * 1.60934; // Convert mph to kph
        } else if (this == MS) {
            newValue = originalValue * 0.44704; // Convert mph to m/s
        } else if (this == KNOTS) {
            newValue = originalValue * 0.868976; // Convert mph to knots
        }
        return String.format("%s: %.1f%s", label, newValue, this.suffix);
    }
}
