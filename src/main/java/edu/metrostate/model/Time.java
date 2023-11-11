package edu.metrostate.model;

import edu.metrostate.utils.TimeUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Time {
    private final Label timeLabel;

    public Time(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void initializeClock() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(1),
                event -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTime() {
        Platform.runLater(() -> timeLabel.setText(TimeUtils.getCurrentTime()));
    }
}