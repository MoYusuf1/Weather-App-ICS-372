package edu.metrostate.model;

import java.util.StringJoiner;

// TODO: user builder pattern
public class City2 {

      public static final City2 UNKNOWN = new City2();
      public static final City2 CITY_NOT_FOUND = new City2();

      private String continent;
      private String country;
      private String region;
      private String city;
      private String zip;
      private double latitude;
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
