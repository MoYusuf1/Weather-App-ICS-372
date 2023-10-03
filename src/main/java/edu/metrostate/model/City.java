package edu.metrostate.model;
public class City extends Location {
    private int cityId;
    private int timeZone;
    private String country;
    private int seaLevel;

    public City(String name, double latitude, double longitude, int cityId, int timeZone, String country, int seaLevel) {
        super(name, latitude, longitude);
        this.cityId = cityId;
        this.timeZone = timeZone;
        this.country = country;
        this.seaLevel = seaLevel;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }
}