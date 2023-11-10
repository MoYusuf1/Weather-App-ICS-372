package edu.metrostate;

import edu.metrostate.model.DistanceUnit;
import edu.metrostate.model.TemperatureUnit;
import edu.metrostate.model.WindSpeedUnit;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private List<PreferencesChangeListener> listeners = new ArrayList<>();
    private TemperatureUnit temperatureUnitPreference;
    private WindSpeedUnit windSpeedUnitPreference;
    private DistanceUnit distanceUnitPreference;

    private static class InstanceHolder {
        // We can use a nested static class to implement lazy initialization
        private static final UserPreferences INSTANCE = new UserPreferences();
    }

    public static UserPreferences getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private UserPreferences() {
        temperatureUnitPreference = TemperatureUnit.CELSIUS;
        windSpeedUnitPreference = WindSpeedUnit.KPH;
        distanceUnitPreference = DistanceUnit.KILOMETERS;
    }

    public interface PreferencesChangeListener {
        void onPreferencesChanged();
    }

    public TemperatureUnit getTemperatureUnitPreference() {
        return temperatureUnitPreference;
    }

    public void setTemperatureUnitPreference(TemperatureUnit temperatureUnitPreference) {
        this.temperatureUnitPreference = temperatureUnitPreference;
        savePreferences();
        notifyChangeListeners();
    }

    public WindSpeedUnit getWindSpeedUnitPreference() {
        return windSpeedUnitPreference;
    }

    public void setWindSpeedUnitPreference(WindSpeedUnit windSpeedUnitPreference) {
        this.windSpeedUnitPreference = windSpeedUnitPreference;
        savePreferences();
        notifyChangeListeners();
    }

    public DistanceUnit getDistanceUnitPreference() {
        return distanceUnitPreference;
    }

    public void setDistanceUnitPreference(DistanceUnit distanceUnitPreference) {
        this.distanceUnitPreference = distanceUnitPreference;
        savePreferences();
        notifyChangeListeners();
    }

    private void savePreferences() {
        // Save preferences to a file or database (functionality to be implemented later)
    }

    public void addChangeListener(PreferencesChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyChangeListeners() {
        for (PreferencesChangeListener listener : listeners) {
            listener.onPreferencesChanged();
        }
    }

}