package edu.metrostate.model.units;

public enum DistanceUnit {
    MILES("Miles", "M"),
    KILOMETERS("Kilometers", "km");


    private final String description;
    private final String suffix;

    DistanceUnit(String description, String suffix) {
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

