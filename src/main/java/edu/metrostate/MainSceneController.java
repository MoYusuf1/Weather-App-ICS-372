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
}
