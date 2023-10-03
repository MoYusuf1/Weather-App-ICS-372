module App {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.metrostate to javafx.fxml;
    exports edu.metrostate;
    exports edu.metrostate.model;
    opens edu.metrostate.model to javafx.fxml;
    exports edu.metrostate.controller;
    opens edu.metrostate.controller to javafx.fxml;
    exports edu.metrostate.listener;
    opens edu.metrostate.listener to javafx.fxml;
    exports edu.metrostate.cache;
    opens edu.metrostate.cache to javafx.fxml;
}