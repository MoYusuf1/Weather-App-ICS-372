package edu.metrostate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainSceneController {

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

    // Event to trigger Menu press
    public void handleMenuClick(ActionEvent actionEvent) {
        System.out.println("You have pressed the menu button!");
    }

    // Event to trigger settings button
    public void handleSettingsClick(ActionEvent actionEvent) {
        System.out.println("You have pressed the settings button!");
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

}
