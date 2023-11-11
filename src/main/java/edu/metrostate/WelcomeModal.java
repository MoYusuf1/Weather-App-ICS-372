package edu.metrostate;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeModal extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setId("welcome-modal");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(25, 25, 25, 25));
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints columnConstraints2 = new ColumnConstraints();
        columnConstraints2.setHgrow(Priority.SOMETIMES);
        grid.getColumnConstraints().addAll(columnConstraints1, columnConstraints2);
        RowConstraints rowConstraints1 = new RowConstraints();
        rowConstraints1.setMinHeight(Double.MIN_VALUE);
        rowConstraints1.setValignment(VPos.TOP);
        rowConstraints1.setVgrow(Priority.SOMETIMES);
        RowConstraints rowConstraints2 = new RowConstraints();
        rowConstraints2.setMinHeight(Double.MIN_VALUE);
        rowConstraints2.setValignment(VPos.TOP);
        rowConstraints2.setVgrow(Priority.SOMETIMES);
        RowConstraints rowConstraints3 = new RowConstraints();
        rowConstraints3.setMinHeight(Double.MIN_VALUE);
        rowConstraints3.setValignment(VPos.TOP);
        rowConstraints3.setVgrow(Priority.SOMETIMES);
        grid.getRowConstraints().addAll(rowConstraints1, rowConstraints2, rowConstraints3);

        Text sceneTitle = new Text("Welcome to Climate Watch");
        sceneTitle.setFont(Font.font("Century Gothic", FontWeight.EXTRA_BOLD, 45));
        grid.add(sceneTitle, 0 , 0 , 2, 1);
        Label welcomeMessage = new Label("Climate Watch is your home for up-to-date weather info!");
        welcomeMessage.setFont(Font.font("Century Gothic", FontWeight.BLACK, 25));
        welcomeMessage.setWrapText(true);
        grid.add(welcomeMessage, 0, 1, 2, 1);
        Label grantAccessMessage = new Label("We want to display the weather for your location. If you are cool with that, press the ALLOW button below." +
                " If you are not cool with that, press the DENY button below and we will use Metropolitan State University in St. Paul, MN instead.");
        grantAccessMessage.setWrapText(true);
        grantAccessMessage.setFont(Font.font("Century Gothic", FontWeight.NORMAL, 20));
        grid.add(grantAccessMessage, 0 , 2, 2, 1);

        Button allowButton = new Button("ALLOW");
        allowButton.setId("allow-button");
        GridPane.setHalignment(allowButton, HPos.CENTER);
        Button denyButton = new Button("DENY");
        denyButton.setId("deny-button");
        GridPane.setHalignment(denyButton, HPos.CENTER);
        grid.add(allowButton, 0, 3);
        grid.add(denyButton, 1, 3);

//        grid.setGridLinesVisible(true);

        allowButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ALLOW button clicked");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        });
        denyButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("DENY button clicked");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        });

        Image icon = new Image(getClass().getResource("/images/weather-icons/main-icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(grid, 750, 500);
        scene.getStylesheets().add(getClass().getResource("/welcome-modal.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Climate Watch | Welcome");
        primaryStage.show();
    }
}
