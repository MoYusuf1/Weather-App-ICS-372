package edu.metrostate.cache;

import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.weather.Weather;

public interface Cache {

    Weather getWeather(String zipCode);
    FiveDayForecast getFiveDayForecast(String zipCode);

}

