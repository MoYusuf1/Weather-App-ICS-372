package edu.metrostate.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.metrostate.model.Weather;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherApiService {

    private static final DateTimeFormatter DATE_TIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE");
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s&units=imperial";
    private static final String FORECAST_API_URL = "https://api.openweathermap.org/data/2.5/forecast?zip=%s&appid=%s&units=imperial";
    private static final String API_KEY = "56a56586750dcac90b3fe2fedaf45f09";

    public Weather getWeather(String zipCode) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, zipCode, API_KEY);
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
            int dewPoint = calculateDewPoint(temperature, humidity);
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

    public static List<Weather> get5DayForecast(String zipCode) {
        List<Weather> forecast2 = new ArrayList<>();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(FORECAST_API_URL, zipCode, API_KEY);
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            String jsonResponse = new Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (isErrorCondition(jsonObject)) {
                return forecast2;
            }
            JsonArray forecastList = jsonObject.getAsJsonArray("list");

            // Process the daily temperature data for the next 5 days
            double tempMin = Double.MAX_VALUE;
            double tempMax = Double.MIN_VALUE;
            for (int i = 0; i < forecastList.size(); i++) {
                JsonObject forecastData = forecastList.get(i).getAsJsonObject();
                JsonObject mainData = forecastData.getAsJsonObject("main");
                double temperatureMin = mainData.get("temp_min").getAsDouble();
                double temperatureMax = mainData.get("temp_max").getAsDouble();
                String dtTxt = forecastData.get("dt_txt").getAsString();

                // Find the daily temperature min and max
                tempMin = Math.min(tempMin, temperatureMin);
                tempMax = Math.max(tempMax, temperatureMax);

                // Check if we have processed all eight intervals for the day
                // Need to do this since API does three-hour intervals
                if ((i + 1) % 8 == 0) {
                    // I need to get the icon from 75% through the day to display an actuate icon
                    int Middlevalue = i - (int) Math.ceil(0.25 * 8);

                    if (Middlevalue >= 0) {
                        // Extract the icon, similar to other API call
                        String middleIcon = forecastList.get(Middlevalue).getAsJsonObject().getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
                        String dayOfWeek = getDayOfWeek(dtTxt);
                        Weather forecastWeather = new Weather()
                                .setTemperatureMin(tempMin)
                                .setTemperatureMax(tempMax)
                                .setIcon(middleIcon)
                                .setDay(dayOfWeek);

                        forecast2.add(forecastWeather);
                    }
                    // Reset daily temperature min and max for the next day
                    tempMin = Double.MAX_VALUE;
                    tempMax = Double.MIN_VALUE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forecast2;
    }

    private static String getDayOfWeek(String dtTxt) {
        // Take the date out of the API call, and then assign it to a day of the week Than format
        // https://www.tutorialspoint.com/formatting-day-of-week-in-eeee-format-in-java
        LocalDateTime dateTime = LocalDateTime.parse(dtTxt, DATE_TIME_INPUT_FORMATTER);
        return dateTime.format(DATE_TIME_OUTPUT_FORMATTER);
    }

    private static boolean isErrorCondition(JsonObject jsonObject) {
        int statusCode = jsonObject.get("cod").getAsInt();
        int errorType = statusCode/100;
        // 4xx = client error, 5xx = server error
        // Prevent NullPointerException situations
        return errorType == 4 || errorType == 5;
    }

    // Need to calculate Dewpoint, since the API call doesn't provide this.
    private int calculateDewPoint(int temperature, double humidity) {
        humidity = humidity / 100.0;
        double alpha = ((17.27 * (double) temperature) / (237.7 + (double) temperature)) + Math.log(humidity);
        double dewPointFahrenheit = (237.7 * alpha) / (17.27 - alpha);
        return (int) dewPointFahrenheit;
    }

}
