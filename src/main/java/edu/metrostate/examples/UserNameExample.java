package edu.metrostate.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserNameExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField text1 = new TextField();
        text1.setPromptText("Enter your name");
        text1.setFocusTraversable(false);
        TextField text2 = new TextField();
        VBox layout = new VBox(5);
        Label label = new Label("What is your name?");
        Button button = new Button("Submit");
        layout.getChildren().addAll(label, text1, text2, button);

        Scene scene1 = new Scene(layout, 400, 250);
        primaryStage.setTitle("User Information");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

}