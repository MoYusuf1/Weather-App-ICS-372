package edu.metrostate;

import edu.metrostate.controller.HomeController;
import edu.metrostate.controller.WelcomeController;
import edu.metrostate.model.UserPreferences;
import edu.metrostate.utils.ImageUtils;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainApp extends Application {

    private HomeController homeController;

    @Override
    public void start(Stage stage) throws Exception {
        displayHome(stage);
        displayWelcome(stage);
    }

    private void displayHome(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/home.fxml"));
        AnchorPane root = fxmlLoader.load();
        HomeController controller = fxmlLoader.getController();
        UserPreferences userPreferences = UserPreferences.getInstance();
        userPreferences.addChangeListener(controller);
        controller.displayDefaults();
        this.homeController = controller;

        // Stop the user from being able to adjust lines in Fivedayforecast
        String[] splitPaneIds = {"#Paneone", "#Panetwo", "#Panethree", "#Panefour", "#Panefive"};
        for (String id : splitPaneIds) {
            SplitPane splitPane = (SplitPane) root.lookup(id);
            splitPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, Event::consume);
        }

        // https://stackoverflow.com/questions/34941411/how-to-get-controller-of-scene-in-javafx8
        Scene scene = new Scene(root, 1300, 800);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());
        // https://www.flaticon.com/free-icon/climate-change_8479898
        Image icon = ImageUtils.getImage("/images/weather-icons/main-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Climate Watch");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void displayWelcome(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/welcome.fxml"));
        GridPane gridPane = fxmlLoader.load();
        WelcomeController controller = fxmlLoader.getController();
        controller.setHomeController(homeController);
        Scene scene = new Scene(gridPane);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(primaryStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}