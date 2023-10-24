package edu.metrostate.cache;
import edu.metrostate.model.Weather;

import java.util.Map;

public interface Cache {
    Weather getWeather(int cityId);

    void addWeather(int cityId, Weather weather);

    boolean containsWeather(int cityId);
}

