package edu.metrostate.controller;

import edu.metrostate.UserPreferences;
import edu.metrostate.model.DistanceUnit;
import edu.metrostate.model.TemperatureUnit;
import edu.metrostate.model.WindSpeedUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class UserPrefController {

    @FXML
    private Text actionTarget;
    @FXML
    private Text temperatureSelected;
    @FXML
    private Text windSpeedSelected;
    @FXML
    private Text distanceSelected;
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



    // Use the singleton instance of UserPreferences
    private UserPreferences userPreferences = UserPreferences.getInstance();

    // This method should be called to initialize the UI with the loaded preferences
    public void initialize() {
        // Initialize the UI with the loaded preferences
        updateUIWithLoadedPreferences();
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent event) {
        actionTarget.setText("");

        TemperatureUnit selectedTemperatureUnit = getSelectedTemperatureUnit();
        WindSpeedUnit selectedWindSpeedUnit = getSelectedWindSpeedUnit();
        DistanceUnit selectedDistanceUnit = getSelectedDistanceUnit();

        if (selectedTemperatureUnit == null || selectedWindSpeedUnit == null) {
            return;
        }

        if (selectedWindSpeedUnit == null || selectedWindSpeedUnit == null) {
            return;
        }

        if (selectedDistanceUnit == null) {
            return;
        }

        userPreferences.setTemperatureUnitPreference(selectedTemperatureUnit);
        userPreferences.setWindSpeedUnitPreference(selectedWindSpeedUnit);
        userPreferences.setDistanceUnitPreference(selectedDistanceUnit);

        temperatureSelected.setText(selectedTemperatureUnit.getDescription());
        windSpeedSelected.setText(selectedWindSpeedUnit.getDescription());
        distanceSelected.setText(selectedDistanceUnit.getDescription());
        actionTarget.setText("Preferences updated successfully.");
    }



    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        actionTarget.setText("Cancel button pressed");
        // Reset the UI to reflect the current preferences
        updateUIWithLoadedPreferences();
    }

    private TemperatureUnit getSelectedTemperatureUnit() {
        RadioButton selectedTemperatureButton = (RadioButton) temperatureGroup.getSelectedToggle();
        if (selectedTemperatureButton == fahrenheitButton) {
            return TemperatureUnit.FAHRENHEIT;
        } else if (selectedTemperatureButton == celsiusButton) {
            return TemperatureUnit.CELSIUS;
        } else if (selectedTemperatureButton == kelvinButton) {
            return TemperatureUnit.KELVIN;
        } else {
            actionTarget.setText("No temperature unit selected. Please select one.");
            return null; // Handle null in the calling method
        }
    }

    private WindSpeedUnit getSelectedWindSpeedUnit() {
        RadioButton selectedWindSpeedButton = (RadioButton) windspeedGroup.getSelectedToggle();
        if (selectedWindSpeedButton == mphButton) {
            return WindSpeedUnit.MPH;
        } else if (selectedWindSpeedButton == msButton) {
            return WindSpeedUnit.MS;
        } else if (selectedWindSpeedButton == knotsButton) {
            return WindSpeedUnit.KNOTS;
        } else {
            actionTarget.setText("No wind speed unit selected. Please select one.");
            return null; // Handle null in the calling method
        }
    }

    private DistanceUnit getSelectedDistanceUnit() {
        RadioButton selectedDistanceButton = (RadioButton) distanceGroup.getSelectedToggle();
        if (selectedDistanceButton == kilometersButton) {
            return DistanceUnit.KILOMETERS;
        } else if (selectedDistanceButton == milesButton) {
            return DistanceUnit.MILES;
        } else {
            actionTarget.setText("No distance unit selected. Please select one.");
            return null; // Handle null in the calling method
        }
    }


    // A method to update the UI with the loaded preferences
    private void updateUIWithLoadedPreferences() {
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

        // Update the text fields with the loaded preferences
        temperatureSelected.setText(temperatureUnit.getDescription());
        windSpeedSelected.setText(windSpeedUnit.getDescription());
        distanceSelected.setText(distanceUnit.getDescription());
    }
}
