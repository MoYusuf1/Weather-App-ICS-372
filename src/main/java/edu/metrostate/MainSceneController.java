package edu.metrostate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class MainSceneController implements ValueChangedListener {

    @FXML
    private Button gearButton;

    @FXML
    private Button menuBarButton;

    @FXML
    public Label first_day;
    public Label second_day;
    public Label third_day;
    public Label fourth_day;
    public Label fifth_day;

    @FXML
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

    public void initialize() {

    }

    @Override
    public void onValueChange(int newValue) {

    }

    //Event to trigger Menu press
    public void handleMenuClick(ActionEvent actionEvent) {
        System.out.println("You have pressed the menu button!");
    }

    //Event to trigger settings button
    public void handleSettingsClick(ActionEvent actionEvent) {
        System.out.println("You have pressed the settings button!");
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
}
