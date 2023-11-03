package edu.metrostate.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.metrostate.model.Weather;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

public class WeatherApiService {

    private static final String API_URL =
            "https://api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s&units=imperial";
    private static final String API_KEY = "56a56586750dcac90b3fe2fedaf45f09";

    public Weather getWeather(String zipCode) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, zipCode, API_KEY);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = new Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            System.out.println(jsonResponse);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (jsonObject.has("cod") && jsonObject.get("cod").getAsInt() == 404) {
                return Weather.CITY_NOT_FOUND;// Handle the case where the city is not found
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
                    .setClouds(clouds)
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

    private int calculateDewPoint(int temperature, double humidity) {
        humidity = humidity / 100.0;
        double alpha = ((17.27 * (double) temperature) / (237.7 + (double) temperature)) + Math.log(humidity);
        double dewPointFahrenheit = (237.7 * alpha) / (17.27 - alpha);
        return (int) dewPointFahrenheit;
    }

}
