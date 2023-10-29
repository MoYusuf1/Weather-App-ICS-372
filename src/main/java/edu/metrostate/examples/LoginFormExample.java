package edu.metrostate.examples;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginFormExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
        grid.add(sceneTitle, 0 , 0 , 2, 1);
        Label userName = new Label("User Name:");
        userName.setFont(Font.font("Tahoma", FontWeight.BOLD, 10));
        grid.add(userName, 0 , 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label password = new Label("Password:");
        password.setFont(Font.font("Tahoma", FontWeight.BOLD, 10));
        grid.add(password, 0 , 2);
        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 2);

        Button button = new Button("Sign in");
        HBox hboxButton = new HBox(20);
        hboxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hboxButton.getChildren().add(button);
        grid.add(hboxButton, 1, 4);

        grid.setGridLinesVisible(true);

        final boolean[] showButtonPressedMessage = {true};
        Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        button.setOnAction(event -> {
            actionTarget.setFill(Color.FIREBRICK);
            actionTarget.setVisible(showButtonPressedMessage[0]);
            actionTarget.setText("Sign in button pressed");
            showButtonPressedMessage[0] = !showButtonPressedMessage[0];
        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.show();
    }
}
