package edu.metrostate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BlurController {

    @FXML
    private StackPane homeScreenBlur;

    @FXML
    private Button closeButton;

    @FXML
    public void closeButtonAction(ActionEvent event) {
        closeStage();
    }

    public void closeStage() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
