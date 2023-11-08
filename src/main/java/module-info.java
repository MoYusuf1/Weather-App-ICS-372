module App {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires com.google.common;
    requires org.apache.logging.log4j;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.xml;

    opens edu.metrostate to javafx.fxml;
    exports edu.metrostate;
    exports edu.metrostate.model;
    opens edu.metrostate.model to javafx.fxml;
    exports edu.metrostate.controller;
    opens edu.metrostate.controller to javafx.fxml;
    exports edu.metrostate.cache;
    opens edu.metrostate.cache to javafx.fxml;
    exports edu.metrostate.examples;
    opens edu.metrostate.examples to javafx.fxml;
}