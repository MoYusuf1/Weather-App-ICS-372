package edu.metrostate.model;

import edu.metrostate.model.City;

public class User {
    private City userCity;
    private int userTimeZone;
    private String userSpeedUnit;
    private String userTemperatureUnit;
    private City[] userPinnedCities;

    public User(City userCity, int userTimeZone, String userSpeedUnit, String userTemperatureUnit, City[] userPinnedCities) {
        this.userCity = userCity;
        this.userTimeZone = userTimeZone;
        this.userSpeedUnit = userSpeedUnit;
        this.userTemperatureUnit = userTemperatureUnit;
        this.userPinnedCities = userPinnedCities;
    }

    public City getUserCity() {
        return userCity;
    }

    public void setUserCity(City userCity) {
        this.userCity = userCity;
    }

    public int getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(int userTimeZone) {
        this.userTimeZone = userTimeZone;
    }

    public String getUserSpeedUnit() {
        return userSpeedUnit;
    }

    public void setUserSpeedUnit(String userSpeedUnit) {
        this.userSpeedUnit = userSpeedUnit;
    }

    public String getUserTemperatureUnit() {
        return userTemperatureUnit;
    }

    public void setUserTemperatureUnit(String userTemperatureUnit) {
        this.userTemperatureUnit = userTemperatureUnit;
    }

    public City[] getUserPinnedCities() {
        return userPinnedCities;
    }

    public void setUserPinnedCities(City[] userPinnedCities) {
        this.userPinnedCities = userPinnedCities;
    }
}
