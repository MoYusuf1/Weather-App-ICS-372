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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene.fxml"));
        AnchorPane root = loader.load();

        MainSceneController mainSceneController = loader.getController();

        Scene scene = new Scene(root, 1300, 800);

        loadStylesheetIntoScene(scene);

        stage.setTitle("Climate Watch");
        stage.setScene(scene);

        // Current Time
        mainSceneController.CurrentTime(getCurrentTime());

        Image Mainweather_Image = new Image("images/02d@2x.png");
        mainSceneController.CurrentTemp("Currently: " + "68\u00B0");
        mainSceneController.LocationName("Maple Grove, Minnesota");

        mainSceneController.MainweatherHigh("High: " + "72");
        mainSceneController.MainweatherLow("Low: " + "53");

        mainSceneController.MainweatherSpeed("Wind Speed: " + " 7.2m/s NW");
        mainSceneController.MainweatherHumidity("Humidity: " + " 59%");
        mainSceneController.MainweatherDewpoint("Dew Point: " + " 38\u00B0F");
        mainSceneController.MainweatherhectoPascals("hectoPascals: " + " 1020hPa");
        mainSceneController.MainweatherUV("UV: " + " 2");
        mainSceneController.MainweatherVisibility("Visibility: " + " 10.0km");

        // Day 1
        mainSceneController.first_day("Monday");
        Image image1 = new Image("images/02d@2x.png");
        mainSceneController.first_high_first_day("High: " + "85");
        mainSceneController.first_low_first_day("Low: " + "72");

        // Day 2
        mainSceneController.second_day("Tuesday");
        Image image2 = new Image("images/10n@2x.png");
        mainSceneController.second_high_first_day("High: " + "70");
        mainSceneController.second_low_first_day("Low: " + "61");

        // Day 3
        mainSceneController.third_day("Wednesday");
        Image image3 = new Image("images/11d@2x.png");
        mainSceneController.third_high_first_day("High: " + "65");
        mainSceneController.third_low_first_day("Low: " + "58");

        // Day 4
        mainSceneController.fourth_day("Thursday");
        Image image4 = new Image("images/03d@2x.png");
        mainSceneController.fourth_high_first_day("High: " + "68");
        mainSceneController.fourth_low_first_day("Low: " + "61");

        // Day 5
        mainSceneController.fifth_day("Friday");
        Image image5 = new Image("images/01d@2x.png");
        mainSceneController.fifth_high_first_day("High: " + "89");
        mainSceneController.fifth_low_first_day("Low: " + "73");

        // A way to disable the divider for the panes by canceling the users mouse event.
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};

        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            // Cancel the drag event
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }

        // Prevent the resize of the screen
        stage.setResizable(false);
        mainSceneController.setImages(Mainweather_Image);
        mainSceneController.setImages(image1, image2, image3, image4, image5);
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

}