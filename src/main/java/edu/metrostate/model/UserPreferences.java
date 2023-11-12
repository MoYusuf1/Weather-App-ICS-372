package edu.metrostate.model;

import edu.metrostate.model.units.DistanceUnit;
import edu.metrostate.model.units.TemperatureUnit;
import edu.metrostate.model.units.WindSpeedUnit;

import java.util.ArrayList;
import java.util.List;

public class UserPreferences {
    private List<PreferencesChangeListener> listeners = new ArrayList<>();
    private TemperatureUnit temperatureUnitPreference;
    private WindSpeedUnit windSpeedUnitPreference;
    private DistanceUnit distanceUnitPreference;

    private static class InstanceHolder {
        private static final UserPreferences INSTANCE = new UserPreferences();
    }

    public static UserPreferences getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private UserPreferences() {
        temperatureUnitPreference = TemperatureUnit.FAHRENHEIT;
        windSpeedUnitPreference = WindSpeedUnit.MPH;
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
        notifyChangeListeners();
    }

    public WindSpeedUnit getWindSpeedUnitPreference() {
        return windSpeedUnitPreference;
    }

    public void setWindSpeedUnitPreference(WindSpeedUnit windSpeedUnitPreference) {
        this.windSpeedUnitPreference = windSpeedUnitPreference;
        notifyChangeListeners();
    }

    public DistanceUnit getDistanceUnitPreference() {
        return distanceUnitPreference;
    }

    public void setDistanceUnitPreference(DistanceUnit distanceUnitPreference) {
        this.distanceUnitPreference = distanceUnitPreference;
        notifyChangeListeners();
    }

    public void addChangeListener(PreferencesChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyChangeListeners() {
        for (PreferencesChangeListener listener : listeners) {
            listener.onPreferencesChanged();
        }
    }

}
