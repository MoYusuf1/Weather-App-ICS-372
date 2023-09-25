package edu.metrostate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    private final ValueStore store;

    public MainApp() {
        this.store = new ValueStore();
    }

    // Test Code
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

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        BorderPane root = loader.load();

        MainSceneController mainSceneController = loader.getController();
        mainSceneController.setValueStore(store);

        MainToolBar mainToolBar = new MainToolBar();
        MainToolBarController mainToolBarController = new MainToolBarController(mainToolBar, store);
        root.setTop(mainToolBar);

        Scene scene = new Scene(root);

        loadStylesheetIntoScene(scene);

        stage.setTitle("ICS 372 - HelloFX");
        stage.setScene(scene);
        stage.show();
    }

    private void loadStylesheetIntoScene(Scene scene) {
        URL stylesheetURL = getClass().getResource("style.css");
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