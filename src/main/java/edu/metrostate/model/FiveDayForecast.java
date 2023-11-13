package edu.metrostate.model;

import edu.metrostate.controller.HomeController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class FiveDayForecast {

    private final HomeController controller;
    private final UserPreferences userPreferences;

    public FiveDayForecast(HomeController controller, UserPreferences userPreferences) {
        this.controller = controller;
        this.userPreferences = userPreferences;
    }

    public void updateDayInfo(List<Weather> forecast) {
        if (forecast == null || forecast.size() < 5) {
            System.out.println("Forecast was null or not 5-day size so skipping day info update");
            return;
        }

        updateDay(forecast.get(0), controller.first_day_image, controller.first_day, controller.first_high_first_day, controller.first_low_first_day);
        updateDay(forecast.get(1), controller.second_day_image, controller.second_day, controller.second_high_first_day, controller.second_low_first_day);
        updateDay(forecast.get(2), controller.third_day_image, controller.third_day, controller.third_high_first_day, controller.third_low_first_day);
        updateDay(forecast.get(3), controller.fourth_day_image, controller.fourth_day, controller.fourth_high_first_day, controller.fourth_low_first_day);
        updateDay(forecast.get(4), controller.fifth_day_image, controller.fifth_day, controller.fifth_high_first_day, controller.fifth_low_first_day);
    }

    private void updateDay(Weather weather, ImageView dayImage, Label dayLabel, Label highLabel, Label lowLabel) {
        dayImage.setImage(new Image(String.format("images/weather-icons/%s@2x.png", weather.getIcon())));
        dayLabel.setText(weather.getDay());
        highLabel.setText(String.format("High: %.0f\u00B0%s", weather.convertTemperature(weather.getTemperatureMax(), userPreferences.getTemperatureUnitPreference()), userPreferences.getTemperatureUnitPreference().getSuffix()));
        lowLabel.setText(String.format("Low: %.0f\u00B0%s", weather.convertTemperature(weather.getTemperatureMin(), userPreferences.getTemperatureUnitPreference()), userPreferences.getTemperatureUnitPreference().getSuffix()));
    }

    private static Image getImage(Weather weather) {
        return new Image(String.format("images/weather-icons/%s@2x.png", weather.getIcon()));
    }

    private static String getDay(Weather weather) {
        return weather.getDay();
    }

    private String getDailyHigh(Weather weather) {
        return String.format("High: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix());
    }

    private String getDailyLow(Weather weather) {
        return String.format("Low: %d\u00B0%s", Math.round(weather.convertTemperature(weather.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix());
    }
}
