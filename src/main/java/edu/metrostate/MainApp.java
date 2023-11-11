package edu.metrostate;

import edu.metrostate.cache.Cache;
import edu.metrostate.cache.InMemoryCache;
import edu.metrostate.controller.MainSceneController;
import edu.metrostate.model.FiveDayForecast;
import edu.metrostate.model.Time;
import edu.metrostate.model.Weather;
import edu.metrostate.service.WeatherApiService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.event.Event;

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

        Cache cache = InMemoryCache.getInstance();
        UserPreferences userPreferences = UserPreferences.getInstance();
        userPreferences.addChangeListener(controller);

        // Use the Time class to initiate the clock
        Time time = new Time(controller.Current_Time);
        time.initializeClock();

        Scene scene = new Scene(root, 1300, 800);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        FiveDayForecast fiveDayForecast = new FiveDayForecast(controller, userPreferences);
        Weather current = cache.getWeather("55106");
        List<Weather> forecast = WeatherApiService.get5DayForecast("55106");

        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = new Image(Objects.requireNonNull(getClass().getResource("/images/weather-icons/main-icon.png")).toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        // Show Main Screen with all values
        Image currentWeatherImage = new Image(Objects.requireNonNull(getClass().getResource("/images/weather-icons/" + current.getIcon() + "@2x.png")).toExternalForm());
        controller.MainImage(currentWeatherImage);
        controller.LocationName(current.getLocationName());
        controller.CurrentTemp(String.format("Currently: %d\u00B0%s", Math.round(current.convertTemperature(current.getTemperature(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherHigh(String.format("High: %d\u00B0%s", Math.round(current.convertTemperature(current.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherLow(String.format("Low: %d\u00B0%s", Math.round(current.convertTemperature(current.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherSpeed(String.format("Wind Speed: %.2f%s", current.convertWindSpeed(current.getWindSpeed(), userPreferences.getWindSpeedUnitPreference()), userPreferences.getWindSpeedUnitPreference().getSuffix()));
        controller.MainweatherHumidity(String.format("Humidity: %d%%", Math.round(current.getHumidity())));
        controller.MainweatherDewpoint(String.format("Dew Point: %d\u00B0%s", Math.round(current.convertTemperature(current.getDewPoint(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherhectoPascals(String.format("hectoPascals: %dhPa", Math.round(current.getPressure())));
        controller.MainweatherUV(String.format("UV: %s", current.getUv()));
        controller.MainweatherVisibility(String.format("Visibility: %.1f%s", current.convertDistance(current.getVisibility(), userPreferences.getDistanceUnitPreference()), userPreferences.getDistanceUnitPreference().getSuffix()));

        // Use the FiveDayForecast to display temps and icon
        fiveDayForecast.updateDayInfo(forecast);

        // Stop the user from being able to adjust lines in Fivedayforecast
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};
        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }
        
        // Prevent Resize of screen
        stage.setResizable(false);
        stage.show();
    }
}