package edu.metrostate.examples;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ButtonActionExample extends Application {

    private Label label;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Pane root = new StackPane();
        label = new Label("Method Reference");
        Button clickMe = new Button("Click Me");
        clickMe.setOnAction(this::handleClickMe);    // method reference
        root.getChildren().addAll(label, clickMe);

        primaryStage.setTitle("Method Reference Test");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
    private void handleClickMe(ActionEvent event) {
        if(label.getEffect() == null) {
            label.setEffect(new BoxBlur());
        } else {
            label.setEffect(null);
        }
    }
}