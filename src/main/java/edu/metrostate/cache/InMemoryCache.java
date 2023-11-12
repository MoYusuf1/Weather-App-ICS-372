package edu.metrostate.cache;

import edu.metrostate.model.City2;
import edu.metrostate.model.Weather;
import edu.metrostate.service.CityApiService;
import edu.metrostate.service.WeatherApiService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements Cache {

    private static final Map<String, Weather> ZIPCODE_WEATHER_MAP = new ConcurrentHashMap<>();
    private static final Map<String, City2> IP_ADDRESS_CITY_MAP = new ConcurrentHashMap<>();

    private final WeatherApiService weatherApiService;
    private final CityApiService cityApiService;

    private static class InstanceHolder {
        // We can use a nested static class to implement lazy initialization
        private static final InMemoryCache INSTANCE = new InMemoryCache();
    }
    public static InMemoryCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private InMemoryCache() {
        this.weatherApiService = new WeatherApiService();
        this.cityApiService = new CityApiService();
    }

    @Override
    public Weather getWeather(String zipCode) {
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

    @Override
    public City2 getCity(String ipAddress) {
        if (!IP_ADDRESS_CITY_MAP.containsKey(ipAddress)) {
            City2 city = cityApiService.getCity(ipAddress);
            System.out.printf("Adding ipAddress %s to cache for city %s", ipAddress, city);
            IP_ADDRESS_CITY_MAP.put(ipAddress, city);
        }
        return IP_ADDRESS_CITY_MAP.get(ipAddress);
    }

}
