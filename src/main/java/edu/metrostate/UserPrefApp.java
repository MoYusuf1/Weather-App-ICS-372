package edu.metrostate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserPrefApp extends Application {

    @Override
    public void start(Stage stage) {
        createUserPreferences(stage);
    }

    private void createUserPreferences(Stage stage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("User Preferences");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label temperatureLabel = new Label("Temperature Unit:");
        temperatureLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(temperatureLabel, 0, 1);

        ToggleGroup temperatureGroup = new ToggleGroup();
        RadioButton fahrenheitButton = new RadioButton("Fahrenheit");
        RadioButton celsiusButton = new RadioButton("Celsius");
        RadioButton kelvinButton = new RadioButton("Kelvin");
        fahrenheitButton.setSelected(true);
        fahrenheitButton.setToggleGroup(temperatureGroup);
        celsiusButton.setToggleGroup(temperatureGroup);
        kelvinButton.setToggleGroup(temperatureGroup);
        HBox temperatureHbox = new HBox(fahrenheitButton, celsiusButton, kelvinButton);
        grid.add(temperatureHbox, 1, 1);

        Label windspeedLabel = new Label("Wind Speed Unit:");
        windspeedLabel.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        grid.add(windspeedLabel, 0, 2);

        ToggleGroup windspeedGroup = new ToggleGroup();
        RadioButton mphButton = new RadioButton("MPH");
        RadioButton msButton = new RadioButton("M/s");
        RadioButton knotsButton = new RadioButton("Knots");
        mphButton.setSelected(true);
        mphButton.setToggleGroup(windspeedGroup);
        msButton.setToggleGroup(windspeedGroup);
        knotsButton.setToggleGroup(windspeedGroup);
        HBox windspeedHbox = new HBox(mphButton, msButton, knotsButton);
        grid.add(windspeedHbox, 1, 2);

        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");
        HBox hboxButton = new HBox(20);
        hboxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hboxButton.getChildren().addAll(submitButton, cancelButton);
        grid.add(hboxButton, 1, 4);

        // below line is useful for debugging
        grid.setGridLinesVisible(true);

        Text temperatureUnitSelected = new Text();
        grid.add(temperatureUnitSelected, 1, 7);
        Text windspeedUnitSelected = new Text();
        grid.add(windspeedUnitSelected, 1, 8);

        Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        submitButton.setOnAction(event -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText("Submit button pressed");
            displaySelectedUnit(temperatureGroup, temperatureUnitSelected);
            displaySelectedUnit(windspeedGroup, windspeedUnitSelected);
        });
        cancelButton.setOnAction(event -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setText("Cancel button pressed");
            temperatureUnitSelected.setText(null);
            windspeedUnitSelected.setText(null);
        });

        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = new Image(getClass().getResource("/images/weather-icons/main-icon.png").toExternalForm());
        stage.setTitle("Climate Watch | User Preferences");
        stage.getIcons().add(icon);
//        stage.initModality(Modality.WINDOW_MODAL); // TODO: set modality when parent is details/list view
//        stage.initOwner(rootNode); // TODO: set owner when parent is details/list view
        stage.setResizable(false);
        Scene scene = new Scene(grid, 750, 500);
        stage.setScene(scene);
        stage.show();
    }

    private static void displaySelectedUnit(ToggleGroup toggleGroup, Text unitSelected) {
        RadioButton selectedButton = (RadioButton) toggleGroup.getSelectedToggle();
        String selectedUnit = selectedButton.getText();
        unitSelected.setText(selectedUnit);
    }

}