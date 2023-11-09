package edu.metrostate.controller;
import edu.metrostate.UserPreferences;
import edu.metrostate.model.Weather;
import edu.metrostate.MainApp;

import edu.metrostate.service.WeatherApiService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainSceneController implements UserPreferences.PreferencesChangeListener {

    @FXML
    private TextField SearchText;


    @FXML
    private Button Searchbutton;

    @FXML
    private Button gearButton;

    @FXML
    private Button menuBarButton;

    // Holds all five day main screen FXMLs
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
    private WeatherApiService weatherApiService;
    private UserPreferences userPreferences = UserPreferences.getInstance();

    // Event to trigger Menu press
    private MainApp mainApp;

    public void handleMenuClick(ActionEvent actionEvent) {
        System.out.println("You have pressed the menu button!");
    }
    // Event to trigger settings button


    public void handleSettingsClick(ActionEvent actionEvent) throws Exception {
        System.out.println("You have pressed the settings button!");
        loadUserPreferencesScreen(getCurrentStage());
    }

    private void loadUserPreferencesScreen(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/user-pref.fxml"));
        Scene scene = new Scene(root, 750, 500);
        scene.getStylesheets().add(getClass().getResource("/user-pref.css").toExternalForm());
        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = new Image(getClass().getResource("/images/weather-icons/main-icon.png").toExternalForm());
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

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    private void handleFindButtonAction(ActionEvent event) {
        try {
            String zipCode = SearchText.getText();
            Weather current = weatherApiService.getWeather(zipCode);
            if (current != Weather.CITY_NOT_FOUND) {
                System.out.println(current.getDescription());
                updateMainWeatherScreen(current);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid ZIP Code");
                alert.setHeaderText("The inputted ZIP code is invalid");
                alert.initModality(Modality.APPLICATION_MODAL);
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

    private void updateMainWeatherScreen(Weather current) {
        Image currentWeatherImage = new Image(getClass().getResource("/images/weather-icons/" + current.getIcon() + "@2x.png").toExternalForm());
        setImages(currentWeatherImage);
        CurrentTemp("Currently: " + current.getTemperature() + "\u00B0F");
        LocationName(current.getLocationName());
        MainweatherHigh("High: " + String.format("%.0f", current.getTemperatureMax()) + "\u00B0F");
        MainweatherLow("Low: " + String.format("%.0f", current.getTemperatureMin()) + "\u00B0F");
        MainweatherSpeed("Wind Speed: " + current.getWindSpeed() + "mph ");
        MainweatherHumidity("Humidity: " + String.format("%.0f", current.getHumidity()) + "%");
        MainweatherDewpoint("Dew Point: " + String.format("%.0f", current.getDewPoint()) + "\u00B0F");
        MainweatherhectoPascals("hectoPascals: " + String.format("%.0f", current.getPressure()) + "hPa");
        MainweatherUV("UV: " + current.getUv());
        MainweatherVisibility("Visibility: " + current.getVisibility() + "km");
    }

    // Holds all the images for each of the five days.
    public void setImages(Image image1, Image image2, Image image3, Image image4, Image image5) {
        first_day_image.setImage(image1);
        second_day_image.setImage(image2);
        third_day_image.setImage(image3);
        fourth_day_image.setImage(image4);
        fifth_day_image.setImage(image5);
    }

    public void setImages(Image image) {
        Mainweather_Image.setImage(image);
    }

    public void CurrentTime(String text) { Current_Time.setText(text); }
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

    public void setWeatherApiService(WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    public WeatherApiService getWeatherApiService() {
        return weatherApiService;
    }

    @Override
    public void onPreferencesChanged() {

        try {
            Weather current = weatherApiService.getWeather("55106");
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
