package edu.metrostate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City extends Location {

    public static final City METRO_STATE_UNIVERSITY = createMetroStateUniversity();

    @JsonProperty("continent_name")
    private String continent;
    @JsonProperty("country_name")
    private String country;
    @JsonProperty("region_name")
    private String region;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zip")
    private String zipCode;

    public City() {
        // default constructor
    }

    public City(String ipAddress, double latitude, double longitude) {
        super(ipAddress, latitude, longitude);
    }

    private static City createMetroStateUniversity() {
        City city = new City("199.17.228.240", 44.95848083496094, -93.07421875);
        city.setContinent("North America")
                .setCountry("United States")
                .setRegion("Minnesota")
                .setCity("Saint Paul")
                .setZipCode("55106");
        return city;
    }

    public String getRegion() {
        return region;
    }

    public City setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getCity() {
        return city;
    }

    public City setCity(String city) {
        this.city = city;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public City setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getContinent() {
        return continent;
    }

    public City setContinent(String continent) {
        this.continent = continent;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public City setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", City.class.getSimpleName() + "[", "]")
                .add("continent='" + continent + "'")
                .add("country='" + country + "'")
                .add("region='" + region + "'")
                .add("city='" + city + "'")
                .add("zipCode='" + zipCode + "'")
                .add(super.toString())
                .toString();
    }
}
