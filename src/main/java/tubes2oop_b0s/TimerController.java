package tubes2oop_b0s;

import com.almasb.fxgl.entity.Preload;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TimerController {
    @FXML
    private Label timerLabel;
    
    @FXML
    private Circle timerFrame;

    private TimerThread timerThread;
    private BooleanProperty timerCompleted = new SimpleBooleanProperty(false);

    public void initialize() {
        timerThread = new TimerThread(30);
        timerThread.start();
        timerLabel.setVisible(true);
        timerFrame.setVisible(true);

        timerLabel.textProperty().bind(timerThread.timeSecondsProperty().asString("%d"));

        timerThread.completedProperty().addListener((obs, wasCompleted, isCompleted) -> {
            if (isCompleted) {
                timerCompleted.set(true);
                onTimerComplete();
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
