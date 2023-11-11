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

    public void updateDayInfo(List<Weather> forecast) {
        if (forecast == null || forecast.size() < 5) {
            System.out.println("Forecast was null or not 5-day size so skipping day info update");
            return;
        }

        Weather day1 = forecast.get(0);
        Weather day2 = forecast.get(1);
        Weather day3 = forecast.get(2);
        Weather day4 = forecast.get(3);
        Weather day5 = forecast.get(4);

        controller.first_day_image.setImage(getImage(day1));
        controller.first_day(getDay(day1));
        controller.first_high_first_day(getDailyHigh(day1));
        controller.first_low_first_day(getDailyLow(day1));

        controller.second_day_image.setImage(getImage(day2));
        controller.second_day(getDay(day2));
        controller.second_high_first_day(getDailyHigh(day2));
        controller.second_low_first_day(getDailyLow(day2));

        controller.third_day_image.setImage(getImage(day3));
        controller.third_day(getDay(day3));
        controller.third_high_first_day(getDailyHigh(day3));
        controller.third_low_first_day(getDailyLow(day3));

        controller.fourth_day_image.setImage(getImage(day4));
        controller.fourth_day(getDay(day4));
        controller.fourth_high_first_day(getDailyHigh(day4));
        controller.fourth_low_first_day(getDailyLow(day4));

        controller.fifth_day_image.setImage(getImage(day5));
        controller.fifth_day(getDay(day5));
        controller.fifth_high_first_day(getDailyHigh(day5));
        controller.fifth_low_first_day(getDailyLow(day5));
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
