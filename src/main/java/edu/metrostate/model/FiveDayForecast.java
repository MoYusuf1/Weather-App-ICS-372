package edu.metrostate.model;

import edu.metrostate.controller.MainSceneController;
import edu.metrostate.UserPreferences;
import javafx.scene.image.Image;

import java.util.List;

public class FiveDayForecast {

    private final MainSceneController controller;
    private final UserPreferences userPreferences;

    public FiveDayForecast(MainSceneController controller, UserPreferences userPreferences) {
        this.controller = controller;
        this.userPreferences = userPreferences;
    }
    // Update Each days info
    public void updateDayInfo(List<Weather> forecast) {
        if (forecast.size() >= 5) {
            Weather day1 = forecast.get(0);
            Weather day2 = forecast.get(1);
            Weather day3 = forecast.get(2);
            Weather day4 = forecast.get(3);
            Weather day5 = forecast.get(4);

            // Day 1 | Show image, day, high, low
            Image image1 = new Image("images/weather-icons/" + forecast.get(0).getIcon() + "@2x.png");
            controller.first_day(forecast.get(0).getDay());
            controller.first_high_first_day(String.format("High: %d\u00B0%s", Math.round(day1.convertTemperature(day1.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
            controller.first_low_first_day(String.format("Low: %d\u00B0%s", Math.round(day1.convertTemperature(day1.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));

            // Day 2 | Show image, day, high, low
            Image image2 = new Image("images/weather-icons/" + forecast.get(1).getIcon() + "@2x.png");
            controller.second_day(forecast.get(1).getDay());
            controller.second_high_first_day(String.format("High: %d\u00B0%s", Math.round(day2.convertTemperature(day2.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
            controller.second_low_first_day(String.format("Low: %d\u00B0%s", Math.round(day2.convertTemperature(day2.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));

            // Day 3 | Show image, day, high, low
            Image image3 = new Image("images/weather-icons/" + forecast.get(2).getIcon() + "@2x.png");
            controller.third_day(forecast.get(2).getDay());
            controller.third_high_first_day(String.format("High: %d\u00B0%s", Math.round(day3.convertTemperature(day3.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
            controller.third_low_first_day(String.format("Low: %d\u00B0%s", Math.round(day3.convertTemperature(day3.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));

            // Day 4 | Show image, day, high, low
            Image image4 = new Image("images/weather-icons/" + forecast.get(3).getIcon() + "@2x.png");
            controller.fourth_day(forecast.get(3).getDay());
            controller.fourth_high_first_day(String.format("High: %d\u00B0%s", Math.round(day4.convertTemperature(day4.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
            controller.fourth_low_first_day(String.format("Low: %d\u00B0%s", Math.round(day4.convertTemperature(day4.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));

            // Day 5 | Show image, day, high, low
            Image image5 = new Image("images/weather-icons/" + forecast.get(4).getIcon() + "@2x.png");
            controller.fifth_day(forecast.get(4).getDay());
            controller.fifth_high_first_day(String.format("High: %d\u00B0%s", Math.round(day5.convertTemperature(day5.getTemperatureMax(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));
            controller.fifth_low_first_day(String.format("Low: %d\u00B0%s", Math.round(day5.convertTemperature(day5.getTemperatureMin(), userPreferences.getTemperatureUnitPreference())), userPreferences.getTemperatureUnitPreference().getSuffix()));

            // Set the images
            controller.setImages(image1, image2, image3, image4, image5);
        }
    }
}
