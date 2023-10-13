package edu.metrostate;

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

        Scene scene = new Scene(root, 1300, 800);

        loadStylesheetIntoScene(scene);

        Weather current = getWeather(44, -93, "56a56586750dcac90b3fe2fedaf45f09");

        System.out.println(current.getDescription());



        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        // Current Time
        controller.CurrentTime(getCurrentTime());

        Image Mainweather_Image = new Image("images/02d@2x.png");
        controller.CurrentTemp("Currently: " + "68\u00B0");
        controller.LocationName("Maple Grove, Minnesota");

        controller.MainweatherHigh("High: " + "72");
        controller.MainweatherLow("Low: " + "53");

        controller.MainweatherSpeed("Wind Speed: " + " 7.2m/s NW");
        controller.MainweatherHumidity("Humidity: " + " 59%");
        controller.MainweatherDewpoint("Dew Point: " + " 38\u00B0F");
        controller.MainweatherhectoPascals("hectoPascals: " + " 1020hPa");
        controller.MainweatherUV("UV: " + " 2");
        controller.MainweatherVisibility("Visibility: " + " 10.0km");

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
        controller.setImages(Mainweather_Image);
        controller.setImages(image1, image2, image3, image4, image5);
        stage.show();


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

    private static void createTestObjects() {
        City testCity1 = new City("Minneapolis", 44.986656, -93.258133, 1, -5,
                "United States", 250);
        City testCity2 = new City("SaintPaul", 44.954445, -93.091301, 1, -5,
                "United States", 285);
        Weather testWeather1 = new Weather( 1.1, 1.1, 1.1, 1.1,
                1.1, "East", 1, 1, 1, 1.1, "Cloudy");
        Weather testWeather2 = new Weather( 1.1, 1.1, 1.1, 1.1,
                1.1, "West", 1, 1, 1, 1.1, "Sunny");
        City[] testCityGroup = {testCity1, testCity2};
        Weather[] testWeatherGroup = {testWeather1, testWeather2};

        User testUser = new User(testCity1, testCity1.getTimeZone(), "mph", "F", testCityGroup);

        FiveDayForecast testForecast= new FiveDayForecast(testWeatherGroup, testCity1);
    }

    private Weather getWeather(double lat, double lon, String apiKey) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", lat, lon, apiKey);
            HttpGet request = new HttpGet(url);

            HttpResponse response = client.execute(request);
            String jsonResponse = new java.util.Scanner(response.getEntity().getContent()).useDelimiter("\\Z").next();
            System.out.println(jsonResponse);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject mainData = jsonObject.getAsJsonObject("main");
            JsonObject windData = jsonObject.getAsJsonObject("wind");
            JsonObject cloudData = jsonObject.getAsJsonObject("clouds");

            double temperature = mainData.get("temp").getAsDouble();
            double temperatureMin = mainData.get("temp_min").getAsDouble();
            double temperatureMax = mainData.get("temp_max").getAsDouble();
            double humidity = mainData.get("humidity").getAsDouble();
            double windSpeed = windData.get("speed").getAsDouble();
            int windDirectionDegrees = windData.get("deg").getAsInt();
            String windDirection = windDirectionDegrees + "°"; // You might want to convert this to actual directions (N, S, E, W, etc.)
            int clouds = cloudData.get("all").getAsInt();
            int sunrise = jsonObject.getAsJsonObject("sys").get("sunrise").getAsInt();
            int sunset = jsonObject.getAsJsonObject("sys").get("sunset").getAsInt();
            double visibility = jsonObject.get("visibility").getAsDouble();
            String description = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

            return new Weather(temperature, temperatureMin, temperatureMax, humidity, windSpeed, windDirection, clouds, sunrise, sunset, visibility, description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}