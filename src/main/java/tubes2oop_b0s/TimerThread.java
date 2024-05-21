package tubes2oop_b0s;

public class TimerThread extends Thread {
    private volatile boolean running = true;
    private final javafx.beans.property.IntegerProperty timeSeconds;
    private final javafx.beans.property.BooleanProperty completed = new javafx.beans.property.SimpleBooleanProperty(false);

    public TimerThread(int countdownSeconds) {
        setDaemon(true);
        this.timeSeconds = new javafx.beans.property.SimpleIntegerProperty(countdownSeconds);
    }

    @Override
    public void run() {
        while (running && timeSeconds.get() > 0) {
            try {
                Thread.sleep(1000);
                javafx.application.Platform.runLater(() -> timeSeconds.set(timeSeconds.get() - 1));
            } catch (InterruptedException e) {
                running = false;
            }
        }
        if (timeSeconds.get() == 0) {
            javafx.application.Platform.runLater(() -> completed.set(true));
        }
    }

    public void stopTimer() {
        running = false;
    }

    public javafx.beans.property.IntegerProperty timeSecondsProperty() {
        return timeSeconds;
    }

    public javafx.beans.property.BooleanProperty completedProperty() {
        return completed;
    }
}
