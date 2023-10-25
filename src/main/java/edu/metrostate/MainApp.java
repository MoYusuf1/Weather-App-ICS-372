package edu.metrostate;

import edu.metrostate.controller.MainSceneController;
import edu.metrostate.model.Weather;
import edu.metrostate.service.WeatherApiService;
import edu.metrostate.utils.TimeUtils;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    private static final FXMLLoader LOADER = new FXMLLoader();

    @Override
    public void start(Stage stage) throws Exception {
        createHomeScreen(stage);
    }

    private void createHomeScreen(Stage stage) throws IOException {
        WeatherApiService weatherApiService = new WeatherApiService();

        LOADER.setLocation(getClass().getResource("/home-scene.fxml"));
        AnchorPane root = LOADER.load();
        MainSceneController controller = LOADER.getController();
        controller.setMainApp(this);
        controller.setWeatherApiService(weatherApiService);

        Scene scene = new Scene(root, 1300, 800);

        loadStylesheetIntoScene(scene);

        Weather current = weatherApiService.getWeather("55369");
        System.out.println(current.getDescription());

        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        controller.CurrentTime(TimeUtils.getCurrentTime());

        Image currentWeatherImage = new Image(getClass().getResource("/images/weather-icons/" + current.getIcon() + "@2x.png").toExternalForm());
        controller.setImages(currentWeatherImage);
        controller.LocationName(current.getLocationName());
        controller.CurrentTemp(String.format("Currently: %s\u00B0F", current.getTemperature()));
        controller.MainweatherHigh(String.format("High: %.0f\u00B0F", current.getTemperatureMax()));
        controller.MainweatherLow(String.format("Low: %.0f\u00B0F", current.getTemperatureMin()));
        controller.MainweatherSpeed(String.format("Wind Speed: %smph", current.getWindSpeed()));
        controller.MainweatherHumidity(String.format("Humidity: %.0f%%", current.getHumidity()));
        controller.MainweatherDewpoint(String.format("Dew Point: %.0f\u00B0F", current.getDewPoint()));
        controller.MainweatherhectoPascals(String.format("hectoPascals: %.0fhPa", current.getPressure()));
        controller.MainweatherUV(String.format("UV: %s", current.getUv()));
        controller.MainweatherVisibility(String.format("Visibility: %skm", current.getVisibility()));

        // Day 1
        controller.first_day("Monday");
        Image image1 = new Image("images/weather-icons/02d@2x.png");
        controller.first_high_first_day("High: " + "85");
        controller.first_low_first_day("Low: " + "72");

        // Day 2
        controller.second_day("Tuesday");
        Image image2 = new Image("images/weather-icons/10n@2x.png");
        controller.second_high_first_day("High: " + "70");
        controller.second_low_first_day("Low: " + "61");

        // Day 3
        controller.third_day("Wednesday");
        Image image3 = new Image("images/weather-icons/11d@2x.png");
        controller.third_high_first_day("High: " + "65");
        controller.third_low_first_day("Low: " + "58");

        // Day 4
        controller.fourth_day("Thursday");
        Image image4 = new Image("images/weather-icons/03d@2x.png");
        controller.fourth_high_first_day("High: " + "68");
        controller.fourth_low_first_day("Low: " + "61");

        // Day 5
        controller.fifth_day("Friday");
        Image image5 = new Image("images/weather-icons/01d@2x.png");
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

}