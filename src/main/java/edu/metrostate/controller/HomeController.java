package edu.metrostate.controller;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.cache.Cache;
import edu.metrostate.cache.InMemoryCache;
import edu.metrostate.model.weather.DailyForecast;
import edu.metrostate.model.weather.FiveDayForecast;
import edu.metrostate.model.Time;
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
        Scene scene = new Scene(root, 750, 500);
        Image icon = ImageUtils.getImage("/images/weather-icons/main-icon.png");
        Stage stage = new Stage();
        stage.getIcons().add(icon);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.setResizable(false);
        stage.setTitle("Climate Watch | User Preferences");
        stage.setScene(scene);
        stage.show();
    }

    private Stage getCurrentStage() {
        return (Stage) currentStageHook.getScene().getWindow();
    }

    public void updateWeatherFromWelcomeModal(String zipCode) {
        this.zipCode = zipCode;
        initializeTime();
        Weather weather = cache.getWeather(zipCode);
        FiveDayForecast fiveDayForecast = cache.getFiveDayForecast(zipCode);
        updateMainWeather(weather);
        updateWeatherDisplay(weather);
        updateFiveDayForecast(fiveDayForecast);
    }

    @FXML
    private void handleFindButtonAction(ActionEvent event) {
        String zipCode = SearchText.getText();
        if (zipCode != null && Objects.equals(this.zipCode, zipCode)) {
            System.out.println(String.format("Skipping find because zipCode didn't change zipCode=%s", zipCode));
            return;
        }
        this.zipCode = zipCode;
        try {
            Weather weather = cache.getWeather(zipCode);
            if (weather != null && weather != Weather.UNKNOWN) {
                // Only need to update the 5-day forecast if we have a legitimate city
                FiveDayForecast fiveDayForecast = cache.getFiveDayForecast(zipCode);
                updateMainWeather(weather);
                updateWeatherDisplay(weather);
                updateFiveDayForecast(fiveDayForecast);
            } else {
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
        } catch (Exception e) {
            // Handle the exception, e.g., show an error message or log it
            e.printStackTrace(); // You can log the exception for debugging
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred while fetching weather data");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

    public void MainImage(Image image) {
        Mainweather_Image.setImage(image);
    }

    public void CurrentTemp(String text) { Current_Temp.setText(text); }
    public void LocationName(String text) { Location_Name.setText(text); }
    public void MainweatherHigh(String text) { Mainweather_High.setText(text); }
    public void MainweatherLow(String text) { Mainweather_Low.setText(text); }
    public void MainweatherSpeed(String text) { Mainweather_Speed.setText(text); }
    public void MainweatherHumidity(String text) { Mainweather_Humidity.setText(text); }
    public void MainweatherDewpoint(String text) { Mainweather_Dewpoint.setText(text); }
    public void MainweatherhectoPascals(String text) { Mainweather_hectoPascals.setText(text); }
    public void MainweatherUV(String text) { Mainweather_UV.setText(text); }
    public void MainweatherVisibility(String text) { Mainweather_Visibility.setText(text); }

    public void first_high_first_day(String text) { first_high_first_day.setText(text); }
    public void first_low_first_day(String text) { first_low_first_day.setText(text); }
    public void second_high_first_day(String text) { second_high_first_day.setText(text); }
    public void second_low_first_day(String text) { second_low_first_day.setText(text); }
    public void third_high_first_day(String text) { third_high_first_day.setText(text); }
    public void third_low_first_day(String text) { third_low_first_day.setText(text); }
    public void fourth_high_first_day(String text) { fourth_high_first_day.setText(text); }
    public void fourth_low_first_day(String text) { fourth_low_first_day.setText(text); }
    public void fifth_high_first_day(String text) { fifth_high_first_day.setText(text); }
    public void fifth_low_first_day(String text) { fifth_low_first_day.setText(text); }

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
        try {
            System.out.println(String.format("Updating onPreferencesChanged called zipCode=%s", zipCode));
            Weather weather = cache.getWeather(zipCode);
            FiveDayForecast fiveDayForecast = cache.getFiveDayForecast(zipCode);
            updateMainWeather(weather);
            updateWeatherDisplay(weather);
            updateFiveDayForecast(fiveDayForecast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateMainWeather(Weather weather) {
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", weather.getIcon()));
        MainImage(currentWeatherImage);
        LocationName(weather.getLocationName());
        MainweatherhectoPascals("hectoPascals: " + String.format("%.0f", weather.getPressure()) + "hPa");
        MainweatherUV("UV: " + weather.getUv());
        MainweatherHumidity("Humidity: " + String.format("%.0f", weather.getHumidity()) + "%");

        CurrentTemp("Currently: " + weather.getTemperature() + "\u00B0F");
        MainweatherHigh("High: " + String.format("%.0f", weather.getTemperatureMax()) + "\u00B0F");
        MainweatherLow("Low: " + String.format("%.0f", weather.getTemperatureMin()) + "\u00B0F");
        MainweatherSpeed("Wind Speed: " + weather.getWindSpeed() + "mph");
        MainweatherDewpoint("Dew Point: " + String.format("%.0f", weather.getDewPoint()) + "\u00B0F");
        MainweatherVisibility("Visibility: " + weather.getVisibility() + "km");
    }

    private void updateWeatherDisplay(Weather weather) {
        CurrentTemp(String.format("Currently: %.1f%s",
                weather.convertTemperature(weather.getTemperature(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherHigh(String.format("High: %.1f%s",
                weather.convertTemperature(weather.getTemperatureMax(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherLow(String.format("Low: %.1f%s",
                weather.convertTemperature(weather.getTemperatureMin(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherSpeed(String.format("Wind Speed: %.1f%s",
                weather.convertWindSpeed(weather.getWindSpeed(), userPreferences.getWindSpeedUnitPreference()),
                userPreferences.getWindSpeedUnitPreference().getSuffix()));

        MainweatherDewpoint(String.format("Dew Point: %.1f%s",
                weather.convertTemperature(weather.getTemperature(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherVisibility(String.format("Visibility: %.1f%s",
                weather.convertDistance(weather.getVisibility(), userPreferences.getDistanceUnitPreference()),
                userPreferences.getDistanceUnitPreference().getSuffix()));
    }

    public void updateFiveDayForecast(FiveDayForecast fiveDayForecast) {
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
        dayImage.setImage(ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", dailyForecast.getIcon())));
        dayLabel.setText(dailyForecast.getDay());
        highLabel.setText(String.format("High: %.0f\u00B0%s",
                dailyForecast.convertTemperature(dailyForecast.getTemperatureMax(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));
        lowLabel.setText(String.format("Low: %.0f\u00B0%s",
                dailyForecast.convertTemperature(dailyForecast.getTemperatureMin(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));
    }

    public void displayDefaults() {
        Weather weather = Weather.UNKNOWN;
        FiveDayForecast fiveDayForecast = FiveDayForecast.UNKNOWN;
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", weather.getIcon()));
        MainImage(currentWeatherImage);
        LocationName(weather.getLocationName());
        setCurrent_Time(Time.DEFAULT_TIME_STRING);
        CurrentTemp(String.format("Currently: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperature(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        MainweatherHigh(String.format("High: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        MainweatherLow(String.format("Low: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        MainweatherSpeed(String.format("Wind Speed: %.2f%s", weather.convertWindSpeed(weather.getWindSpeed(), userPreferences.getWindSpeedUnitPreference()), userPreferences.getWindSpeedUnitPreference().getSuffix()));
        MainweatherHumidity(String.format("Humidity: %d%%", Math.round(weather.getHumidity())));
        MainweatherDewpoint(String.format("Dew Point: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getDewPoint(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
        MainweatherhectoPascals(String.format("hectoPascals: %dhPa", Math.round(weather.getPressure())));
        MainweatherUV(String.format("UV: %s", weather.getUv()));
        MainweatherVisibility(String.format("Visibility: %.1f%s", weather.convertDistance(weather.getVisibility(), userPreferences.getDistanceUnitPreference()), userPreferences.getDistanceUnitPreference().getSuffix()));
        updateFiveDayForecast(fiveDayForecast);
    }

}
