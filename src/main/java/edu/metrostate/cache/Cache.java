package edu.metrostate.cache;
import edu.metrostate.model.Weather;

import java.util.Map;

public class Cache {
    private Map<Integer, Weather> cache;

    public Cache(Map<Integer, Weather> cache) {
        this.cache = cache;
    }

    public Weather getWeather(int cityId) {
        return cache.get(cityId);
    }

    public void addWeather(int cityId, Weather weather) {
        cache.put(cityId, weather);
    }

    public boolean containsWeather(int cityId) {
        return cache.containsKey(cityId);
    }
}

