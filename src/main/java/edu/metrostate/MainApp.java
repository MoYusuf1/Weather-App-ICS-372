package edu.metrostate;

import edu.metrostate.controller.HomeController;
import edu.metrostate.controller.WelcomeController;
import edu.metrostate.model.Time;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.weather.Weather;
import edu.metrostate.utils.ImageUtils;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private HomeController homeController;

    @Override
    public void start(Stage stage) throws Exception {
        loadHome(stage);
        displayWelcome(stage);
    }

    private void loadHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        AnchorPane root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        this.homeController = controller;

        UserPreferences userPreferences = UserPreferences.getInstance();
        userPreferences.addChangeListener(controller);
        Weather weather = Weather.UNKNOWN;
        FiveDayForecast fiveDayForecast = FiveDayForecast.UNKNOWN;

        // Show Main Screen with all values
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", weather.getIcon()));
        controller.MainImage(currentWeatherImage);
        controller.LocationName(weather.getLocationName());
        controller.setCurrent_Time(Time.DEFAULT_TIME_STRING);
        controller.CurrentTemp(String.format("Currently: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperature(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherHigh(String.format("High: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherLow(String.format("Low: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherSpeed(String.format("Wind Speed: %.2f%s", weather.convertWindSpeed(weather.getWindSpeed(), userPreferences.getWindSpeedUnitPreference()), userPreferences.getWindSpeedUnitPreference().getSuffix()));
        controller.MainweatherHumidity(String.format("Humidity: %d%%", Math.round(weather.getHumidity())));
        controller.MainweatherDewpoint(String.format("Dew Point: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getDewPoint(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        controller.MainweatherhectoPascals(String.format("hectoPascals: %dhPa", Math.round(weather.getPressure())));
        controller.MainweatherUV(String.format("UV: %s", weather.getUv()));
        controller.MainweatherVisibility(String.format("Visibility: %.1f%s", weather.convertDistance(weather.getVisibility(), userPreferences.getDistanceUnitPreference()), userPreferences.getDistanceUnitPreference().getSuffix()));
        controller.updateFiveDayForecast(fiveDayForecast);

        // Stop the user from being able to adjust lines in Fivedayforecast
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};
        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }

        // https://stackoverflow.com/questions/34941411/how-to-get-controller-of-scene-in-javafx8
        Scene scene = new Scene(root, 1300, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = ImageUtils.getImage("/images/weather-icons/main-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Climate Watch");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    private void displayWelcome(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        GridPane gridPane = fxmlLoader.load();
        WelcomeController controller = fxmlLoader.getController();
        controller.setHomeController(homeController);
        Scene scene = new Scene(gridPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}