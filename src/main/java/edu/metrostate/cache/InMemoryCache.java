package edu.metrostate.cache;

import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.weather.Weather;
import edu.metrostate.service.WeatherApiService;
import edu.metrostate.utils.ZipCodeUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements Cache {

    private static final Map<String, Weather> ZIPCODE_WEATHER_MAP = new ConcurrentHashMap<>();
    private static final Map<String, FiveDayForecast> ZIPCODE_FIVE_DAY_FORECAST_MAP = new ConcurrentHashMap<>();

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
        if (ZipCodeUtils.isInvalidZipCode(zipCode)) {
            System.out.println("Provided zipCode was not 5 digits so skipping weather lookup");
            return null;
        }
        if (!ZIPCODE_WEATHER_MAP.containsKey(zipCode)) {
            Weather weather = weatherApiService.getWeather(zipCode);
            if (Weather.UNKNOWN == weather) {
                System.out.println(String.format("Invalid zipCode provided [%s] so not caching weather", zipCode));
            } else {
                System.out.println(String.format("Valid zipCode provided [%s] so caching it for weather %s", zipCode, weather));
                ZIPCODE_WEATHER_MAP.put(zipCode, weather);
            }
        }
        return ZIPCODE_WEATHER_MAP.get(zipCode);
    }

    @Override
    public FiveDayForecast getFiveDayForecast(String zipCode) {
        if (ZipCodeUtils.isInvalidZipCode(zipCode)) {
            System.out.println("Provided zipCode was not 5 digits so skipping five day forecast lookup");
            return null;
        }
        if (!ZIPCODE_FIVE_DAY_FORECAST_MAP.containsKey(zipCode)) {
            FiveDayForecast fiveDayForecast = weatherApiService.get5DayForecast(zipCode);
            if (FiveDayForecast.UNKNOWN == fiveDayForecast) {
                System.out.println(String.format("Invalid zipCode provided [%s] so not caching five day forecast", zipCode));
            } else {
                System.out.println(String.format("Valid zipCode provided [%s] so caching it for five day forecast %s", zipCode, fiveDayForecast));
                ZIPCODE_FIVE_DAY_FORECAST_MAP.put(zipCode, fiveDayForecast);
            }
        }
        return ZIPCODE_FIVE_DAY_FORECAST_MAP.get(zipCode);
    }

}
