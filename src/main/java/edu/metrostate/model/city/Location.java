package edu.metrostate.model.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Location {

    @JsonProperty("ip")
    private String ipAddress;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;

    public Location(String ipAddress, double latitude, double longitude) {
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
        // default constructor
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Location setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Location setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Location.class.getSimpleName() + "[", "]")
                .add("ipAddress='" + ipAddress + "'")
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .toString();
    }
}