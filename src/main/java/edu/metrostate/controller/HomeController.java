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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;


public class HomeController implements UserPreferences.PreferencesChangeListener {

    @FXML
    private TextField SearchText;
    @FXML
    private Label first_day;
    @FXML
    private Label second_day;
    @FXML
    private Label third_day;
    @FXML
    private Label fourth_day;
    @FXML
    private Label fifth_day;
    @FXML
    private Label first_high_first_day;
    @FXML
    private Label first_low_first_day;
    @FXML
    private Label second_high_first_day;
    @FXML
    private Label second_low_first_day;
    @FXML
    private Label third_high_first_day;
    @FXML
    private Label third_low_first_day;
    @FXML
    private Label fourth_high_first_day;
    @FXML
    private Label fourth_low_first_day;
    @FXML
    private Label fifth_high_first_day;
    @FXML
    private Label fifth_low_first_day;
    @FXML
    private ImageView first_day_image;
    @FXML
    private ImageView second_day_image;
    @FXML
    private ImageView third_day_image;
    @FXML
    private ImageView fourth_day_image;
    @FXML
    private ImageView fifth_day_image;

    @FXML
    private Pane Mainweather;
    @FXML
    private Label Current_Time;
    @FXML
    private Time time;
    @FXML
    private Label Current_Temp;
    @FXML
    private Label Location_Name;
    @FXML
    private ImageView Mainweather_Image;
    @FXML
    private Label Mainweather_High;
    @FXML
    private Label Mainweather_Low;
    @FXML
    private Label Mainweather_Speed;
    @FXML
    private Label Mainweather_Humidity;
    @FXML
    private Label Mainweather_Dewpoint;
    @FXML
    private Label Mainweather_hectoPascals;
    @FXML
    private Label Mainweather_UV;
    @FXML
    private Label Mainweather_Visibility;
    @FXML
    private Label currentStageHook;

    private String zipCode;

    private final Cache cache = InMemoryCache.getInstance();
    private final UserPreferences userPreferences = UserPreferences.getInstance();

    @FXML
    public void initializeTime() {
        time = new Time(Current_Time);
        time.initializeClock();
    }

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

    private Stage getCurrentStage() {
        return (Stage) currentStageHook.getScene().getWindow();
    }

    public void updateWeatherFromWelcomeModal(String zipCode) {
        this.zipCode = zipCode;
        initializeTime();
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
        String zipCode = SearchText.getText();
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

    public void MainImage(Image image) {
        Mainweather_Image.setImage(image);
    }

    public void CurrentTemp(String text) {
        Current_Temp.setText(text);
    }

    public void LocationName(String text) {
        Location_Name.setText(text);
    }

    public void MainweatherHigh(String text) {
        Mainweather_High.setText(text);
    }

    public void MainweatherLow(String text) {
        Mainweather_Low.setText(text);
    }

    public void MainweatherSpeed(String text) {
        Mainweather_Speed.setText(text);
    }

    public void MainweatherHumidity(String text) {
        Mainweather_Humidity.setText(text);
    }

    public void MainweatherDewpoint(String text) {
        Mainweather_Dewpoint.setText(text);
    }

    public void MainweatherhectoPascals(String text) {
        Mainweather_hectoPascals.setText(text);
    }

    public void MainweatherUV(String text) {
        Mainweather_UV.setText(text);
    }

    public void MainweatherVisibility(String text) {
        Mainweather_Visibility.setText(text);
    }

    public void first_high_first_day(String text) {
        first_high_first_day.setText(text);
    }

    public void first_low_first_day(String text) {
        first_low_first_day.setText(text);
    }

    public void second_high_first_day(String text) {
        second_high_first_day.setText(text);
    }

    public void second_low_first_day(String text) {
        second_low_first_day.setText(text);
    }

    public void third_high_first_day(String text) {
        third_high_first_day.setText(text);
    }

    public void third_low_first_day(String text) {
        third_low_first_day.setText(text);
    }

    public void fourth_high_first_day(String text) {
        fourth_high_first_day.setText(text);
    }

    public void fourth_low_first_day(String text) {
        fourth_low_first_day.setText(text);
    }

    public void fifth_high_first_day(String text) {
        fifth_high_first_day.setText(text);
    }

    public void fifth_low_first_day(String text) {
        fifth_low_first_day.setText(text);
    }

    public void first_day(String text) {
        first_day.setText(text);
    }

    public void second_day(String text) {
        second_day.setText(text);
    }

    public void third_day(String text) {
        third_day.setText(text);
    }

    public void fourth_day(String text) {
        fourth_day.setText(text);
    }

    public void fifth_day(String text) {
        fifth_day.setText(text);
    }

    public void setCurrent_Time(String text) {
        Current_Time.setText(text);
    }

    public Label getCurrent_Time() {
        return Current_Time;
    }

    public HomeController setCurrent_Time(Label current_Time) {
        Current_Time = current_Time;
        return this;
    }

    @Override
    public void onPreferencesChanged() {
        System.out.printf("Updating onPreferencesChanged called zipCode=%s%n", zipCode);
        getWeatherAndForecastAndDisplay(zipCode);
    }

    private void updateMainWeatherDisplay(Weather weather) {
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", weather.getIcon()));
        MainImage(currentWeatherImage);
        LocationName(weather.getLocationName());
        MainweatherhectoPascals(String.format("hectoPascals: %.0fhPa", weather.getPressure()));
        MainweatherUV(String.format("UV: %s", weather.getUv()));
        MainweatherHumidity(String.format("Humidity: %.0f%%", weather.getHumidity()));

        CurrentTemp(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Currently", weather.getTemperature()));
        MainweatherHigh(userPreferences.getTemperatureUnitPreference().convertAndDisplay("High", weather.getTemperatureMax()));
        MainweatherLow(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Low", weather.getTemperatureMin()));
        MainweatherDewpoint(userPreferences.getTemperatureUnitPreference().convertAndDisplay("Dew Point", weather.getTemperature()));
        MainweatherSpeed(userPreferences.getWindSpeedUnitPreference().convertAndDisplay("Wind Speed", weather.getWindSpeed()));
        MainweatherVisibility(userPreferences.getDistanceUnitPreference().convertAndDisplay("Visibility", weather.getVisibility()));
    }

    private void updateFiveDayForecastDisplay(FiveDayForecast fiveDayForecast) {
        DailyForecast day1 = fiveDayForecast.getDay1();
        DailyForecast day2 = fiveDayForecast.getDay2();
        DailyForecast day3 = fiveDayForecast.getDay3();
        DailyForecast day4 = fiveDayForecast.getDay4();
        DailyForecast day5 = fiveDayForecast.getDay5();

        updateDailyForecast(day1, first_day_image, first_day, first_high_first_day, first_low_first_day);
        updateDailyForecast(day2, second_day_image, second_day, second_high_first_day, second_low_first_day);
        updateDailyForecast(day3, third_day_image, third_day, third_high_first_day, third_low_first_day);
        updateDailyForecast(day4, fourth_day_image, fourth_day, fourth_high_first_day, fourth_low_first_day);
        updateDailyForecast(day5, fifth_day_image, fifth_day, fifth_high_first_day, fifth_low_first_day);
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

}
