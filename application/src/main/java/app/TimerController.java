package app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class TimerController {
    @FXML
    private Label timerLabel;
    
    @FXML
    private Circle timerFrame;

    private TimerThread timerThread;
    private  SongPlayer songPlayer;
    
    private BooleanProperty timerCompleted = new SimpleBooleanProperty(false);

    public void initialize() {
        songPlayer = new SongPlayer("bear.mp3", 50);
        songPlayer.play();
        int low = 30;
        int result =  (int) (Math.random() * 31) + low;
        timerThread = new TimerThread(result);
        timerThread.start();
        timerLabel.setVisible(true);
        timerFrame.setVisible(true);

        timerLabel.textProperty().bind(timerThread.timeSecondsProperty().asString("%d"));

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
        // Additional actions to be taken when the timer completes
//        timerLabel.setVisible(false);
//        timerFrame.setVisible(false);
        
//        do something about logic
    }

    public void stopTimer() {
        if (timerThread != null) {
            timerThread.stopTimer();
        }
    }
}