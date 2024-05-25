package app;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.beans.binding.Bindings;
import javafx.scene.shape.Circle;

public class TimerController {
    @FXML
    private Label timerLabel;

    @FXML
    private Circle timerFrame;

    private TimerThread timerThread;
    private SongPlayer songPlayer;

    private BooleanProperty timerCompleted = new SimpleBooleanProperty(false);

    public void initialize() {
        songPlayer = new SongPlayer("bear.mp3", 100);
        songPlayer.play();
        int low = 30;
        int result = (int) (Math.random() * 30) + low;
        timerThread = new TimerThread(result);
        timerThread.start();
        timerLabel.setVisible(true);
        timerFrame.setVisible(true);

        timerThread.timeTenthsProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> {
                timerLabel.setText(String.format("%d.%d",
                        timerThread.getTimeSeconds(),
                        timerThread.timeTenthsProperty().get()));
            });
        });


        timerThread.completedProperty().addListener((obs, wasCompleted, isCompleted) -> {
            if (isCompleted) {
                timerCompleted.set(true);
                onTimerComplete();
                songPlayer.stop();
            }
        });
    }

    public BooleanProperty timerCompletedProperty() {
        return timerCompleted;
    }

    private void onTimerComplete() {
        System.out.println("Timer has completed!");
    }

    public void stopTimer() {
        if (timerThread != null) {
            timerThread.stopTimer();
        }
    }
}
