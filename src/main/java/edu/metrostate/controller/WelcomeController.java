package edu.metrostate.controller;

import edu.metrostate.model.city.City;
import edu.metrostate.service.CityApiService;
import edu.metrostate.utils.IpUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeController {

    @FXML
    private GridPane welcomeModal;
    @FXML
    private Text welcomeTitle;
    @FXML
    private Label welcomeMessage;
    @FXML
    private Label grantAccessMessage;
    @FXML
    private Button allowButton;
    @FXML
    private Button denyButton;

    private HomeController homeController;
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }

    private CityApiService cityApiService = new CityApiService();

    @FXML
    public void handleAllowButtonAction(ActionEvent event) {
        String ipAddress = IpUtils.getIpAddress();
        City city = cityApiService.getCity(ipAddress);
        String zipCode = city.getZipCode();
        homeController.updateWeatherFromWelcomeModal(zipCode);

        // close() vs hide()
        // https://stackoverflow.com/questions/54047392/how-to-close-only-one-stage-in-java-fx-without-exiting-all-app
        Stage welcomeStage = (Stage) allowButton.getScene().getWindow();
        welcomeStage.close();
    }

    @FXML
    public void handleDenyButtonAction(ActionEvent event) {
        String zipCode = City.METRO_STATE_UNIVERSITY.getZipCode();
        homeController.updateWeatherFromWelcomeModal(zipCode);

        Stage welcomeStage = (Stage) denyButton.getScene().getWindow();
        welcomeStage.close();
    }

}
