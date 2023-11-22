package edu.metrostate;

import edu.metrostate.controller.HomeController;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.utils.ImageUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        displayHome(stage);
    }

    private void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        AnchorPane root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        // https://stackoverflow.com/questions/34941411/how-to-get-controller-of-scene-in-javafx8
        Scene scene = new Scene(root);
        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = ImageUtils.getImage("/images/weather-icons/main-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Climate Watch");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        UserPreferences userPreferences = UserPreferences.getInstance();
        userPreferences.addChangeListener(controller);
        controller.disablePaneResizing(root);
        controller.displayDefaults();
        controller.displayWelcome(stage);
    }

}