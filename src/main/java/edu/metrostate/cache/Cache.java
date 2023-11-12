package edu.metrostate.cache;

import edu.metrostate.model.Weather;

public interface Cache {
    Weather getWeather(String zipCode);

}

