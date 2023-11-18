package edu.metrostate.controller;

import edu.metrostate.model.UserPreferences;
import edu.metrostate.model.units.DistanceUnit;
import edu.metrostate.model.units.TemperatureUnit;
import edu.metrostate.model.units.WindSpeedUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserPrefController implements Initializable {

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
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
    private ToggleGroup temperatureGroup;
    @FXML
    private ToggleGroup windspeedGroup;
    @FXML
    private RadioButton kilometersButton;
    @FXML
    private RadioButton milesButton;
    @FXML
    private ToggleGroup distanceGroup;

    private final UserPreferences userPreferences = UserPreferences.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TemperatureUnit temperatureUnit = userPreferences.getTemperatureUnitPreference();
        WindSpeedUnit windSpeedUnit = userPreferences.getWindSpeedUnitPreference();
        DistanceUnit distanceUnit = userPreferences.getDistanceUnitPreference();

        fahrenheitButton.setSelected(temperatureUnit == TemperatureUnit.FAHRENHEIT);
        celsiusButton.setSelected(temperatureUnit == TemperatureUnit.CELSIUS);
        kelvinButton.setSelected(temperatureUnit == TemperatureUnit.KELVIN);

        knotsButton.setSelected(windSpeedUnit == WindSpeedUnit.KNOTS);
        mphButton.setSelected(windSpeedUnit == WindSpeedUnit.MPH);
        msButton.setSelected(windSpeedUnit == WindSpeedUnit.MS);

        kilometersButton.setSelected(distanceUnit == DistanceUnit.KILOMETERS);
        milesButton.setSelected(distanceUnit == DistanceUnit.MILES);
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        TemperatureUnit selectedTemperatureUnit = getSelectedTemperatureUnit();
        if (userPreferences.getTemperatureUnitPreference() != selectedTemperatureUnit) {
            userPreferences.setTemperatureUnitPreference(selectedTemperatureUnit);
        }

        WindSpeedUnit selectedWindSpeedUnit = getSelectedWindSpeedUnit();
        if (userPreferences.getWindSpeedUnitPreference() != selectedWindSpeedUnit) {
            userPreferences.setWindSpeedUnitPreference(selectedWindSpeedUnit);
        }

        DistanceUnit selectedDistanceUnit = getSelectedDistanceUnit();
        if (userPreferences.getDistanceUnitPreference() != selectedDistanceUnit) {
            userPreferences.setDistanceUnitPreference(selectedDistanceUnit);
        }

        // get a handle to the stage and close it
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        // get a handle to the stage and close it
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private TemperatureUnit getSelectedTemperatureUnit() {
        // We default the radio button selection up front in UserPreferences#loadPreferences so null isn't possible
        RadioButton selectedTemperatureButton = (RadioButton) temperatureGroup.getSelectedToggle();
        if (selectedTemperatureButton == fahrenheitButton) {
            return TemperatureUnit.FAHRENHEIT;
        } else if (selectedTemperatureButton == celsiusButton) {
            return TemperatureUnit.CELSIUS;
        } else {
            return TemperatureUnit.KELVIN;
        }
    }

    private WindSpeedUnit getSelectedWindSpeedUnit() {
        // We default the radio button selection up front in UserPreferences#loadPreferences so null isn't possible
        RadioButton selectedWindSpeedButton = (RadioButton) windspeedGroup.getSelectedToggle();
        if (selectedWindSpeedButton == mphButton) {
            return WindSpeedUnit.MPH;
        } else if (selectedWindSpeedButton == msButton) {
            return WindSpeedUnit.MS;
        } else {
            return WindSpeedUnit.KNOTS;
        }
    }

    private DistanceUnit getSelectedDistanceUnit() {
        // We default the radio button selection up front in UserPreferences#loadPreferences so null isn't possible
        RadioButton selectedDistanceButton = (RadioButton) distanceGroup.getSelectedToggle();
        if (selectedDistanceButton == kilometersButton) {
            return DistanceUnit.KILOMETERS;
        } else {
            return DistanceUnit.MILES;
        }
    }

}
