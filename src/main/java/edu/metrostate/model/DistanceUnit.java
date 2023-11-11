package edu.metrostate.model;

public enum DistanceUnit {
    MILES("Miles", "M"), // Representing miles
    KILOMETERS("Kilometers", "km"); // Representing kilometers


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

