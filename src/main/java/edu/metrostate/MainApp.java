package edu.metrostate;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

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
        AnchorPane root = loader.load();

        MainSceneController mainSceneController = loader.getController();

        Scene scene = new Scene(root, 1300, 800);

        loadStylesheetIntoScene(scene);

        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        mainSceneController.first_day("Monday");
        mainSceneController.second_day("Tuesday");
        mainSceneController.third_day("Wednesday");
        mainSceneController.fourth_day("Thursday");
        mainSceneController.fifth_day("Friday");


        // A way to disable the divider for the panes by canceling the users mouse event.
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};

        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            // Cancel the drag event
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }

        stage.setResizable(false);

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