package edu.metrostate.controller;

import edu.metrostate.model.TemperatureUnit;
import edu.metrostate.model.WindSpeedUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class UserPrefController {

    @FXML
    private Text actionTarget;
    @FXML
    private Text temperatureSelected;
    @FXML
    private Text windSpeedSelected;
    @FXML
    private RadioButton fahrenheitButton;
    @FXML
    private RadioButton celsiusButton;
    @FXML
    private RadioButton kelvinButton;
    @FXML
    private RadioButton mphButton;
    @FXML
    private RadioButton msButton;
    @FXML
    private RadioButton knotsButton;

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        actionTarget.setText("Submit button pressed");
        temperatureSelected.setText(getSelectedTemperature());
        windSpeedSelected.setText(getSelectedWindSpeed());
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        actionTarget.setText("Cancel button pressed");
        temperatureSelected.setText(null);
        windSpeedSelected.setText(null);
    }

    private String getSelectedTemperature() {
        if (fahrenheitButton.isSelected()) {
            return TemperatureUnit.FAHRENHEIT.getDescription();
        } else if (celsiusButton.isSelected()) {
            return TemperatureUnit.CELSIUS.getDescription();
        } else if (kelvinButton.isSelected()){
            return TemperatureUnit.KELVIN.getDescription();
        } else {
            throw new IllegalStateException("Invalid temperature unit selected");
        }
    }

    private String getSelectedWindSpeed() {
        if (knotsButton.isSelected()) {
            return WindSpeedUnit.KNOTS.getDescription();
        } else if (mphButton.isSelected()) {
            return WindSpeedUnit.MPH.getDescription();
        } else if (msButton.isSelected()) {
            return WindSpeedUnit.MS.getDescription();
        } else {
            throw new IllegalStateException("Invalid wind speed unit selected");
        }
    }
}
