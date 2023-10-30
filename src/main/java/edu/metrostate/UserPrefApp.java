package edu.metrostate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// hook up event listener to list graphic in main screen and store result in user dto
public class UserPrefApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/user-pref.fxml"));
        Scene scene = new Scene(root, 750, 500);
        scene.getStylesheets().add(getClass().getResource("/user-pref.css").toExternalForm());
        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = new Image(getClass().getResource("/images/weather-icons/main-icon.png").toExternalForm());
        stage.getIcons().add(icon);
//        stage.initModality(Modality.WINDOW_MODAL); // TODO: set modality when parent is details/list view
//        stage.initOwner(rootNode); // TODO: set owner when parent is details/list view
        stage.setResizable(false);
        stage.setTitle("Climate Watch | User Preferences");
        stage.setScene(scene);
        stage.show();
    }

}