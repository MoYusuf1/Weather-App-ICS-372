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

    public String convertAndDisplay(String label, double originalValue) {
        double newValue = originalValue;
        if (this == KILOMETERS) {
            newValue = originalValue;
        } else if (this == MILES) {
            newValue = originalValue * (1 / 1.60934); // Convert kilometers to miles
        }
        return String.format(label, newValue, this.suffix);
    }
}

