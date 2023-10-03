package edu.metrostate.model;

public class FiveDayForecast {
    private Weather[] currentForecast;
    private City currentCity;

    public Weather[] getCurrentForecast() {
        return currentForecast;
    }

    public FiveDayForecast(Weather[] currentForecast, City currentCity) {
        this.currentForecast = currentForecast;
        this.currentCity = currentCity;
    }

    public void setCurrentForecast(Weather[] currentForecast) {
        this.currentForecast = currentForecast;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }
}
