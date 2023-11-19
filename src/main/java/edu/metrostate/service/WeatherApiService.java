package edu.metrostate.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.metrostate.model.weather.DailyForecast;
import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.weather.Weather;
import edu.metrostate.utils.TimeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherApiService {

    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s&units=imperial";
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?zip=%s&appid=%s&units=imperial";
    private static final String API_KEY = "56a56586750dcac90b3fe2fedaf45f09";

    public Weather getWeather(String zipCode) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(WEATHER_API_URL, zipCode, API_KEY);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = new Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (isErrorCondition(jsonObject)) {
                return Weather.UNKNOWN;
            }

            JsonObject mainData = jsonObject.getAsJsonObject("main");
            JsonObject windData = jsonObject.getAsJsonObject("wind");
            JsonObject cloudData = jsonObject.getAsJsonObject("clouds");

            int temperature = mainData.get("temp").getAsInt();
            double temperatureMin = mainData.get("temp_min").getAsDouble();
            double temperatureMax = mainData.get("temp_max").getAsDouble();
            double humidity = mainData.get("humidity").getAsDouble();
            double windSpeed = windData.get("speed").getAsDouble();
            int windDirectionDegrees = windData.get("deg").getAsInt();
            String windDirection = windDirectionDegrees + "°";
            int clouds = cloudData.get("all").getAsInt();
            int sunrise = jsonObject.getAsJsonObject("sys").get("sunrise").getAsInt();
            int sunset = jsonObject.getAsJsonObject("sys").get("sunset").getAsInt();
            double visibility = jsonObject.get("visibility").getAsDouble() / 1000;
            String description = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            String icon = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            double pressure = jsonObject.getAsJsonObject("main").has("pressure") ? jsonObject.getAsJsonObject("main").get("pressure").getAsDouble() : 0.0;
            int uv = 0;
            int dewPoint = Weather.calculateDewPoint(temperature, humidity);
            String locationName = jsonObject.get("name").getAsString();

            return new Weather()
                    .setTemperature(temperature)
                    .setTemperatureMin(temperatureMin)
                    .setTemperatureMax(temperatureMax)
                    .setHumidity(humidity)
                    .setWindSpeed(windSpeed)
                    .setHumidity(humidity)
                    .setWindDirection(windDirection)
                    .setSunrise(sunrise)
                    .setSunset(sunset)
                    .setVisibility(visibility)
                    .setDescription(description)
                    .setIcon(icon)
                    .setDewPoint(dewPoint)
                    .setPressure(pressure)
                    .setUv(uv)
                    .setLocationName(locationName);
        } catch (Exception e) {
            e.printStackTrace();
            return Weather.UNKNOWN;
        }
    }

    public FiveDayForecast get5DayForecast(String zipCode) {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(FORECAST_API_URL, zipCode, API_KEY);
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String jsonResponse = new Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (isErrorCondition(jsonObject)) {
                return new FiveDayForecast();
            }
            JsonArray forecastList = jsonObject.getAsJsonArray("list");

            // Process the daily temperature data for the next 5 days
            double tempMin = Double.MAX_VALUE;
            double tempMax = Double.MIN_VALUE;
            int entriesAdded = 0; // Counter for entries added
            for (int i = 0; i < forecastList.size() - 1; i++) {
                JsonObject forecastData = forecastList.get(i).getAsJsonObject();
                JsonObject mainData = forecastData.getAsJsonObject("main");
                double temperatureMin = mainData.get("temp_min").getAsDouble();
                double temperatureMax = mainData.get("temp_max").getAsDouble();
                String dtTxt = forecastData.get("dt_txt").getAsString();

                // Find the daily temperature min and max
                tempMin = Math.min(tempMin, temperatureMin);
                tempMax = Math.max(tempMax, temperatureMax);

                // Grabs the noon icon
                if (dtTxt.contains("12:00:00")) {
                    String middleIcon = getIcon(forecastList, i+1);

                    String dayOfWeek = TimeUtils.getDayOfWeek(dtTxt);
                    DailyForecast dailyForecast = new DailyForecast()
                            .setTemperatureMin(tempMin)
                            .setTemperatureMax(tempMax)
                            .setIcon(middleIcon)
                            .setDay(dayOfWeek);
                    dailyForecasts.add(dailyForecast);
                    // Reset daily temperature min and max for the next day
                    tempMin = Double.MAX_VALUE;
                    tempMax = Double.MIN_VALUE;
                    entriesAdded++;

                    // Break the loop when 5 entries are added
                    if (entriesAdded == 5) {
                        break;
                    }
                }
            }
            // Incase we don't have a noon icon, we grab the last value.
            if (entriesAdded < 5 && forecastList.size() > 0) {
                // Use the last available data in the list
                JsonObject lastForecastData = forecastList.get(forecastList.size() - 1).getAsJsonObject();
                JsonObject lastMainData = lastForecastData.getAsJsonObject("main");
                double lastTemperatureMin = lastMainData.get("temp_min").getAsDouble();
                double lastTemperatureMax = lastMainData.get("temp_max").getAsDouble();
                String lastDtTxt = lastForecastData.get("dt_txt").getAsString();

                String lastMiddleIcon = getIcon(forecastList, forecastList.size() - 1);

                String lastDayOfWeek = TimeUtils.getDayOfWeek(lastDtTxt);
                DailyForecast lastDailyForecast = new DailyForecast()
                        .setTemperatureMin(lastTemperatureMin)
                        .setTemperatureMax(lastTemperatureMax)
                        .setIcon(lastMiddleIcon)
                        .setDay(lastDayOfWeek);
                dailyForecasts.add(lastDailyForecast);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FiveDayForecast.UNKNOWN;
        }
        return new FiveDayForecast(dailyForecasts);
    }

    private static String getIcon(JsonArray forecastList, int middleValue) {
        return forecastList.get(middleValue).getAsJsonObject()
                .getAsJsonArray("weather").get(0)
                .getAsJsonObject().get("icon").getAsString();
    }

    private static boolean isErrorCondition(JsonObject jsonObject) {
        int statusCode = jsonObject.get("cod").getAsInt();
        int errorType = statusCode / 100;
        // 4xx = client error, 5xx = server error
        // Prevent NullPointerException situations
        return errorType == 4 || errorType == 5;
    }

}
