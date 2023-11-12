package edu.metrostate.cache;

import edu.metrostate.model.Weather;
import edu.metrostate.service.WeatherApiService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements Cache {

    private static final Map<String, Weather> ZIPCODE_WEATHER_MAP = new ConcurrentHashMap<>();

    private final WeatherApiService weatherApiService;

    private static class InstanceHolder {
        private static final InMemoryCache INSTANCE = new InMemoryCache();
    }

    public static InMemoryCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private InMemoryCache() {
        this.weatherApiService = new WeatherApiService();
    }

    @Override
    public Weather getWeather(String zipCode) {
        if (zipCode == null || zipCode.isBlank()) {
            System.out.println("Provided zipCode was null or blank so skipping lookup");
            return null;
        }
        if (!ZIPCODE_WEATHER_MAP.containsKey(zipCode)) {
            Weather weather = weatherApiService.getWeather(zipCode);
            if (Weather.UNKNOWN == weather) {
                System.out.printf("Invalid zipCode provided [%s] so not caching it", zipCode);
            } else {
                System.out.printf("Valid zipCode provided [%s] so caching it for weather %s", zipCode, weather);
                ZIPCODE_WEATHER_MAP.put(zipCode, weather);
            }
        }
        return ZIPCODE_WEATHER_MAP.get(zipCode);
    }

}
