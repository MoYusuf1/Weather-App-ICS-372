package edu.metrostate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City2 {

      public static final City2 DEFAULT = new City2()
              .setContinent("North America")
              .setCountry("United States")
              .setRegion("Minnesota")
              .setCity("Saint Paul")
              .setZip("55106")
              .setLatitude(44.95848083496094)
              .setLongitude(-93.07421875);

      @JsonProperty("continent_name")
      private String continent;
      @JsonProperty("country_name")
      private String country;
      @JsonProperty("region_name")
      private String region;
      @JsonProperty("city")
      private String city;
      @JsonProperty("zip")
      private String zip;
      @JsonProperty("latitude")
      private double latitude;
      @JsonProperty("longitude")
      private double longitude;

      public String getContinent() {
            return continent;
      }

      public City2 setContinent(String continent) {
            this.continent = continent;
            return this;
      }

      public String getCountry() {
            return country;
      }

      public City2 setCountry(String country) {
            this.country = country;
            return this;
      }

      public String getRegion() {
            return region;
      }

      public City2 setRegion(String region) {
            this.region = region;
            return this;
      }

      public String getCity() {
            return city;
      }

      public City2 setCity(String city) {
            this.city = city;
            return this;
      }

      public String getZip() {
            return zip;
      }

      public City2 setZip(String zip) {
            this.zip = zip;
            return this;
      }

      public double getLatitude() {
            return latitude;
      }

      public City2 setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
      }

      public double getLongitude() {
            return longitude;
      }

      public City2 setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
      }

      @Override
      public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            City2 city2 = (City2) o;
            return Double.compare(latitude, city2.latitude) == 0
                    && Double.compare(longitude, city2.longitude) == 0
                    && Objects.equals(continent, city2.continent)
                    && Objects.equals(country, city2.country)
                    && Objects.equals(region, city2.region)
                    && Objects.equals(city, city2.city)
                    && Objects.equals(zip, city2.zip);
      }

      @Override
      public int hashCode() {
            return Objects.hash(continent, country, region, city, zip, latitude, longitude);
      }

      @Override
      public String toString() {
            return new StringJoiner(", ", City2.class.getSimpleName() + "[", "]")
                    .add("continent='" + continent + "'")
                    .add("country='" + country + "'")
                    .add("region='" + region + "'")
                    .add("city='" + city + "'")
                    .add("zip='" + zip + "'")
                    .add("latitude=" + latitude)
                    .add("longitude=" + longitude)
                    .toString();
      }
}
