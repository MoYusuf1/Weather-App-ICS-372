package edu.metrostate.model;

// TODO: add conversion from one unit to another
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
}
