package edu.metrostate.cache;
import edu.metrostate.model.City2;
import edu.metrostate.model.Weather;

public interface Cache {
    Weather getWeather(String zipCode);

    City2 getCity(String ipAddress);

}

