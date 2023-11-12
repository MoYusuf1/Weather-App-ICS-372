package edu.metrostate.controller;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.cache.Cache;
import edu.metrostate.cache.InMemoryCache;
import edu.metrostate.model.FiveDayForecast;
import edu.metrostate.model.Time;
import edu.metrostate.model.Weather;

import edu.metrostate.service.WeatherApiService;
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
import java.util.List;
import java.util.Objects;

public class HomeController implements UserPreferences.PreferencesChangeListener {

    @FXML
    private TextField SearchText;
    @FXML
    public Label first_day;
    public Label second_day;
    public Label third_day;
    public Label fourth_day;
    public Label fifth_day;
    public Label first_high_first_day;
    public Label first_low_first_day;
    public Label second_high_first_day;
    public Label second_low_first_day;
    public Label third_high_first_day;
    public Label third_low_first_day;
    public Label fourth_high_first_day;
    public Label fourth_low_first_day;
    public Label fifth_high_first_day;
    public Label fifth_low_first_day;
    public ImageView first_day_image, second_day_image, third_day_image, fourth_day_image, fifth_day_image;

    @FXML
    public Pane Mainweather;
    public Label Current_Time;
    private Time time;
    public Label Current_Temp;
    public Label Location_Name;
    public ImageView Mainweather_Image;
    public Label Mainweather_High;
    public Label Mainweather_Low;
    public Label Mainweather_Speed;
    public Label Mainweather_Humidity;
    public Label Mainweather_Dewpoint;
    public Label Mainweather_hectoPascals;
    public Label Mainweather_UV;
    public Label Mainweather_Visibility;
    @FXML
    public Label currentStageHook;
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
        initializeTime();
        Weather current = cache.getWeather(zipCode);
        List<Weather> forecast = WeatherApiService.get5DayForecast(zipCode);
        FiveDayForecast fiveDayForecast = new FiveDayForecast(this, userPreferences);
        updateMainWeatherScreen(current);
        fiveDayForecast.updateDayInfo(forecast);
    }

    @FXML
    private void handleFindButtonAction(ActionEvent event) {
        try {
            String zipCode = SearchText.getText();
            Weather current = cache.getWeather(zipCode);
            if (current != null && current != Weather.UNKNOWN) {
                // Only need to update the 5-day forecast if we have a legitimate city
                List<Weather> forecast = WeatherApiService.get5DayForecast(zipCode);
                FiveDayForecast fiveDayForecast = new FiveDayForecast(this, userPreferences);
                updateMainWeatherScreen(current);
                fiveDayForecast.updateDayInfo(forecast);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Climate Watch | Invalid ZIP Code");
                alert.setHeaderText(null);
                alert.setGraphic(null);
                Label label = new Label("""
                        The inputted ZIP code is invalid. Try these examples:
                        
                        * Louisville, Kentucky -- 40202
                        * Des Moines, Iowa -- 50309
                        * Chicago, Illinois -- 60601
                        * St. Louis, Missouri -- 63101
                        * Houston, Texas -- 77036
                        * Denver, Colorado -- 80202
                        * San Francisco, California -- 94111
                        * Seattle, Washington -- 98101
                        * Las Vegas, Nevada -- 89101
                        * New York, New York -- 10001""");
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

    // Update the Main weather values
    private void updateMainWeatherScreen(Weather current) {
        Image currentWeatherImage = ImageUtils.getImage(String.format("/images/weather-icons/%s@2x.png", current.getIcon()));
        MainImage(currentWeatherImage);
        CurrentTemp("Currently: " + current.getTemperature() + "\u00B0F");
        LocationName(current.getLocationName());
        MainweatherHigh("High: " + String.format("%.0f", current.getTemperatureMax()) + "\u00B0F");
        MainweatherLow("Low: " + String.format("%.0f", current.getTemperatureMin()) + "\u00B0F");
        MainweatherSpeed("Wind Speed: " + current.getWindSpeed() + "mph");
        MainweatherHumidity("Humidity: " + String.format("%.0f", current.getHumidity()) + "%");
        MainweatherDewpoint("Dew Point: " + String.format("%.0f", current.getDewPoint()) + "\u00B0F");
        MainweatherhectoPascals("hectoPascals: " + String.format("%.0f", current.getPressure()) + "hPa");
        MainweatherUV("UV: " + current.getUv());
        MainweatherVisibility("Visibility: " + current.getVisibility() + "km");
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

    @Override
    public void onPreferencesChanged() {
        try {
            // TODO: need to use dynamic zip code
            Weather current = cache.getWeather("55106");
            updateWeatherDisplay(current);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateWeatherDisplay(Weather current) {

        CurrentTemp(String.format("Currently: %.1f%s",
                current.convertTemperature(current.getTemperature(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherHigh(String.format("High: %.1f%s",
                current.convertTemperature(current.getTemperatureMax(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherLow(String.format("Low: %.1f%s",
                current.convertTemperature(current.getTemperatureMin(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherSpeed(String.format("Wind Speed: %.1f%s",
                current.convertWindSpeed(current.getWindSpeed(), userPreferences.getWindSpeedUnitPreference()),
                userPreferences.getWindSpeedUnitPreference().getSuffix()));

        MainweatherDewpoint(String.format("Dew Point: %.1f%s",
                current.convertTemperature(current.getTemperature(), userPreferences.getTemperatureUnitPreference()),
                userPreferences.getTemperatureUnitPreference().getSuffix()));

        MainweatherVisibility(String.format("Visibility: %.1f%s",
                current.convertDistance(current.getVisibility(), userPreferences.getDistanceUnitPreference()),
                userPreferences.getDistanceUnitPreference().getSuffix()));

    }

}
