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

    public InMemoryCache(WeatherApiService weatherApiService, CityApiService cityApiService) {
        this.weatherApiService = weatherApiService;
        this.cityApiService = cityApiService;
    }

    @Override
    public Weather getWeather(String zipCode) {
        if (!ZIPCODE_WEATHER_MAP.containsKey(zipCode)) {
            Weather weather = weatherApiService.getWeather(zipCode);
            ZIPCODE_WEATHER_MAP.put(zipCode, weather);
        }
        return ZIPCODE_WEATHER_MAP.get(zipCode);
    }

    @Override
    public City2 getCity(String ipAddress) {
        if (!IP_ADDRESS_CITY_MAP.containsKey(ipAddress)) {
            City2 city = cityApiService.getCity(ipAddress);
            IP_ADDRESS_CITY_MAP.put(ipAddress, city);
        }
        return IP_ADDRESS_CITY_MAP.get(ipAddress);
    }

}
