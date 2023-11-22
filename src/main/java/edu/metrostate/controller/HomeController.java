package edu.metrostate.controller;

import edu.metrostate.cache.Cache;
import edu.metrostate.cache.InMemoryCache;
import edu.metrostate.model.Time;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.model.weather.DailyForecast;
import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.weather.Weather;
import edu.metrostate.utils.ImageUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HomeController implements UserPreferences.PreferencesChangeListener {

    @FXML
    private TextField searchText;
    @FXML
    private Label firstDayName;
    @FXML
    private Label secondDayName;
    @FXML
    private Label thirdDayName;
    @FXML
    private Label fourthDayName;
    @FXML
    private Label fifthDayName;
    @FXML
    private Label firstDayHighTemp;
    @FXML
    private Label firstDayLowTemp;
    @FXML
    private Label secondDayHighTemp;
    @FXML
    private Label secondDayLowTemp;
    @FXML
    private Label thirdDayHighTemp;
    @FXML
    private Label thirdDayLowTemp;
    @FXML
    private Label fourthDayHighTemp;
    @FXML
    private Label fourthDayLowTemp;
    @FXML
    private Label fifthDayHighTemp;
    @FXML
    private Label fifthDayLowTemp;
    @FXML
    private ImageView firstDayImage;
    @FXML
    private ImageView secondDayImage;
    @FXML
    private ImageView thirdDayImage;
    @FXML
    private ImageView fourthDayImage;
    @FXML
    private ImageView fifthDayImage;
    @FXML
    private Pane mainWeatherPane;
    @FXML
    private Label mainWeatherCurrentTime;
    @FXML
    private Label mainWeatherCurrentTemp;
    @FXML
    private Label mainWeatherLocationName;
    @FXML
    private ImageView mainWeatherImage;
    @FXML
    private Label mainWeatherHighTemp;
    @FXML
    private Label mainWeatherLowTemp;
    @FXML
    private Label mainWeatherSpeed;
    @FXML
    private Label mainWeatherHumidity;
    @FXML
    private Label mainWeatherDewpoint;
    @FXML
    private Label mainWeatherHectoPascals;
    @FXML
    private Label mainWeatherUv;
    @FXML
    private Label mainWeatherVisibility;
    @FXML
    private Label currentStageHook;

    private String zipCode;

    private final Cache cache = InMemoryCache.getInstance();
    private final UserPreferences userPreferences = UserPreferences.getInstance();

    public void handleSettingsClick(ActionEvent actionEvent) throws Exception {
        loadUserPreferencesScreen(getCurrentStage());
    }

    private void loadUserPreferencesScreen(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/user-pref.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void displayWelcome(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        GridPane gridPane = fxmlLoader.load();
        WelcomeController controller = fxmlLoader.getController();
        controller.setHomeController(this);
        Scene scene = new Scene(gridPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private Stage getCurrentStage() {
        return (Stage) currentStageHook.getScene().getWindow();
    }

    public void updateWeatherFromWelcomeModal(String zipCode) {
        this.zipCode = zipCode;
        Time time = new Time(mainWeatherCurrentTime);
        time.initializeClock();
        getWeatherAndForecastAndDisplay(zipCode);
    }

    private void getWeatherAndForecastAndDisplay(String zipCode) {
        Weather weather = cache.getWeather(zipCode);
        FiveDayForecast fiveDayForecast = cache.getFiveDayForecast(zipCode);
        updateMainWeatherDisplay(weather);
        updateFiveDayForecastDisplay(fiveDayForecast);
    }

    @FXML
    private void handleFindButtonAction(ActionEvent event) {
        String zipCode = searchText.getText();
        if (zipCode != null && Objects.equals(this.zipCode, zipCode)) {
            System.out.printf("Skipping find because zipCode didn't change zipCode=%s%n", zipCode);
            return;
        }
        Weather weather = cache.getWeather(zipCode);
        if (weather == null || weather == Weather.UNKNOWN) {
            alertInvalidZipCode();
        } else {
            updateWeatherAndForecastDisplay(zipCode, weather);
        }
    }

    private void alertInvalidZipCode() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Climate Watch | Invalid ZIP Code");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        Label label = new Label("""
                The inputted ZIP code is invalid. Try these examples:
                                
                * Anchorage, Alaska -- 99501
                * Chicago, Illinois -- 60601
                * Denver, Colorado -- 80202
                * Honolulu, Hawaii -- 96807
                * Houston, Texas -- 77036
                * Las Vegas, Nevada -- 89101
                * Louisville, Kentucky -- 40202
                * New York, New York -- 10001
                * San Francisco, California -- 94111
                * Seattle, Washington -- 98101""");
        label.setStyle("-fx-text-fill: black; -fx-font-family: \"Century Gothic\"");
        label.setWrapText(true);
        alert.getDialogPane().setContent(label);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(getCurrentStage());
        alert.showAndWait();
    }

    private void updateWeatherAndForecastDisplay(String zipCode, Weather weather) {
        this.zipCode = zipCode;
        FiveDayForecast fiveDayForecast = cache.getFiveDayForecast(zipCode);
        updateMainWeatherDisplay(weather);
        updateFiveDayForecastDisplay(fiveDayForecast);
    }

    @Override
    public void onPreferencesChanged() {
        System.out.printf("Updating onPreferencesChanged called zipCode=%s%n", zipCode);
        getWeatherAndForecastAndDisplay(zipCode);
    }

    private void updateMainWeatherDisplay(Weather weather) {
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", weather.getIcon()));
        mainWeatherImage.setImage(currentWeatherImage);
        mainWeatherLocationName.setText(weather.getLocationName());
        mainWeatherHectoPascals.setText(String.format("hectoPascals: %.0fhPa", weather.getPressure()));
        mainWeatherUv.setText(String.format("UV: %s", weather.getUv()));
        mainWeatherHumidity.setText(String.format("Humidity: %.0f%%", weather.getHumidity()));

        mainWeatherCurrentTemp.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Currently", weather.getTemperature()));
        mainWeatherHighTemp.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("High", weather.getTemperatureMax()));
        mainWeatherLowTemp.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Low", weather.getTemperatureMin()));
        mainWeatherDewpoint.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Dew Point", weather.getTemperature()));
        mainWeatherSpeed.setText(userPreferences.getWindSpeedUnitPreference().convertAndDisplay("Wind Speed", weather.getWindSpeed()));
        mainWeatherVisibility.setText(userPreferences.getDistanceUnitPreference().convertAndDisplay("Visibility", weather.getVisibility()));
    }

    private void updateFiveDayForecastDisplay(FiveDayForecast fiveDayForecast) {
        DailyForecast day1 = fiveDayForecast.getDay1();
        DailyForecast day2 = fiveDayForecast.getDay2();
        DailyForecast day3 = fiveDayForecast.getDay3();
        DailyForecast day4 = fiveDayForecast.getDay4();
        DailyForecast day5 = fiveDayForecast.getDay5();

        updateDailyForecast(day1, firstDayImage, firstDayName, firstDayHighTemp, firstDayLowTemp);
        updateDailyForecast(day2, secondDayImage, secondDayName, secondDayHighTemp, secondDayLowTemp);
        updateDailyForecast(day3, thirdDayImage, thirdDayName, thirdDayHighTemp, thirdDayLowTemp);
        updateDailyForecast(day4, fourthDayImage, fourthDayName, fourthDayHighTemp, fourthDayLowTemp);
        updateDailyForecast(day5, fifthDayImage, fifthDayName, fifthDayHighTemp, fifthDayLowTemp);
    }

    private void updateDailyForecast(DailyForecast dailyForecast, ImageView dayImage, Label dayLabel, Label highLabel, Label lowLabel) {
        dayLabel.setText(dailyForecast.getDay());
        dayImage.setImage(ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", dailyForecast.getIcon())));
        highLabel.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("High", dailyForecast.getTemperatureMax()));
        lowLabel.setText(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Low", dailyForecast.getTemperatureMin()));
    }

    public void displayDefaults() {
        Weather weather = Weather.UNKNOWN;
        FiveDayForecast fiveDayForecast = FiveDayForecast.UNKNOWN;
        updateMainWeatherDisplay(weather);
        updateFiveDayForecastDisplay(fiveDayForecast);
    }

    public void disablePaneResizing(AnchorPane root) {
        // Stop the user from being able to adjust lines in Fivedayforecast
        String[] splitPaneIds = {"#firstDayPane", "#secondDayPane", "#thirdDayPane", "#fourthDayPane", "#fifthDayPane"};
        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }
    }

}
