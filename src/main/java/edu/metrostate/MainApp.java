package edu.metrostate;

import com.google.gson.JsonElement;
import edu.metrostate.controller.MainSceneController;
import edu.metrostate.model.City;
import edu.metrostate.model.FiveDayForecast;
import edu.metrostate.model.User;
import edu.metrostate.model.Weather;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.net.URL;

public class MainApp extends Application {

    private static final FXMLLoader LOADER = new FXMLLoader();

    @Override
    public void start(Stage stage) throws Exception {
        createHomeScreen(stage);
    }

    private void createHomeScreen(Stage stage) throws IOException {
        LOADER.setLocation(getClass().getResource("/home-scene.fxml"));
        AnchorPane root = LOADER.load();
        MainSceneController controller = LOADER.getController();
        controller.setMainApp(this);

        Scene scene = new Scene(root, 1300, 800);

        loadStylesheetIntoScene(scene);

        Weather current = getWeather("55369", "56a56586750dcac90b3fe2fedaf45f09");

        System.out.println(current.getDescription());



        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        controller.CurrentTime(getCurrentTime());

        Image currentWeatherImage = new Image(getClass().getResource("/images/" + current.getIcon() + "@2x.png").toExternalForm());
        controller.setImages(currentWeatherImage);
        controller.CurrentTemp("Currently: " + current.getTemperature() + "\u00B0F");
        controller.LocationName(current.getLocationName());
        controller.MainweatherHigh("High: " + String.format("%.0f", current.getTemperatureMax()) + "\u00B0F");
        controller.MainweatherLow("Low: " + String.format("%.0f", current.getTemperatureMin()) + "\u00B0F");
        controller.MainweatherSpeed("Wind Speed: " + current.getWindSpeed() + "mph ");
        controller.MainweatherHumidity("Humidity: " + String.format("%.0f", current.getHumidity()) + "%");
        controller.MainweatherDewpoint("Dew Point: " + String.format("%.0f", current.getDewPoint()) + "\u00B0F");
        controller.MainweatherhectoPascals("hectoPascals: " + String.format("%.0f", current.getPressure()) + "hPa");
        controller.MainweatherUV("UV: " + current.getUV());
        controller.MainweatherVisibility("Visibility: " + current.getVisibility() + "km");

        // Day 1
        controller.first_day("Monday");
        Image image1 = new Image("images/02d@2x.png");
        controller.first_high_first_day("High: " + "85");
        controller.first_low_first_day("Low: " + "72");

        // Day 2
        controller.second_day("Tuesday");
        Image image2 = new Image("images/10n@2x.png");
        controller.second_high_first_day("High: " + "70");
        controller.second_low_first_day("Low: " + "61");

        // Day 3
        controller.third_day("Wednesday");
        Image image3 = new Image("images/11d@2x.png");
        controller.third_high_first_day("High: " + "65");
        controller.third_low_first_day("Low: " + "58");

        // Day 4
        controller.fourth_day("Thursday");
        Image image4 = new Image("images/03d@2x.png");
        controller.fourth_high_first_day("High: " + "68");
        controller.fourth_low_first_day("Low: " + "61");

        // Day 5
        controller.fifth_day("Friday");
        Image image5 = new Image("images/01d@2x.png");
        controller.fifth_high_first_day("High: " + "89");
        controller.fifth_low_first_day("Low: " + "73");

        // A way to disable the divider for the panes by canceling the users mouse event.
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};

        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            // Cancel the drag event
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }

        // Prevent the resize of the screen
        stage.setResizable(false);
        controller.setImages(image1, image2, image3, image4, image5);
        stage.show();
    }

    private void updateUI(Weather current) {
        MainSceneController controller = LOADER.getController();
        controller.updateUI(current);
    }

    private void loadStylesheetIntoScene(Scene scene) {
        URL stylesheetURL = getClass().getResource("/style.css");
        if (stylesheetURL == null) {
            return;
        }
        String urlString = stylesheetURL.toExternalForm();
        if (urlString == null) {
            return;
        }
        scene.getStylesheets().add(urlString);
    }


    private String getCurrentTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return currentTime.format(formatter);
    }
    public static Weather getWeather(String zipCode, String apiKey) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?zip=%s&appid=%s&units=imperial", zipCode, apiKey);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = new java.util.Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            System.out.println(jsonResponse);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
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
            double visibility = jsonObject.get("visibility").getAsDouble();
            String description = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
            String icon = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
            double pressure = jsonObject.getAsJsonObject("main").has("pressure") ? jsonObject.getAsJsonObject("main").get("pressure").getAsDouble() : 0.0;
            int uv = 0;
            String locationName = jsonObject.get("name").getAsString();

            Weather weather = new Weather(temperature, temperatureMin, temperatureMax, humidity, windSpeed, windDirection, clouds, sunrise, sunset, visibility, description, icon, 0.0, pressure, 0, locationName);

            // Calculate dew point
            int dewPoint = weather.calculateDewPoint();
            weather.setDewPoint(dewPoint);

            return weather;
        } catch (Exception e) {
            e.printStackTrace();
            return new Weather(32, 0.0, 0.0, 0.0, 0.0, "N/A", 0, 0, 0, 0.0, "N/A", "N/A", 0.0, 0, 0, "NULL");
        }
    }
}