package app;

public class TimerThread extends Thread {
    private volatile boolean running = true;
    // Using an internal counter to handle tenths of seconds.
    private  int timeSeconds;
    private final javafx.beans.property.IntegerProperty timeTenths;
    private final javafx.beans.property.BooleanProperty completed = new javafx.beans.property.SimpleBooleanProperty(false);

    public TimerThread(int countdownSeconds) {
        setDaemon(true);
        this.timeSeconds = countdownSeconds--;
        this.timeTenths = new javafx.beans.property.SimpleIntegerProperty( 9); // Convert seconds to tenths of seconds for internal tracking
    }

    @Override
    public void run() {
        while (running && timeSeconds > 0) {
            try {
                Thread.sleep(100); // Sleep for 100 milliseconds
                // Decrement the tenths of seconds counter
                javafx.application.Platform.runLater(() -> timeTenths.set(timeTenths.get()-1));
            } catch (InterruptedException e) {
                running = false;
            }
            if (timeTenths.get() <= 0 && timeSeconds > 0) {
                timeTenths.set(9);
                timeSeconds --;
            }
        }
        for (int i = 0; i < 9; i++) {
            try {
                Thread.sleep(100); // Sleep for 100 milliseconds
                // Decrement the tenths of seconds counter
                javafx.application.Platform.runLater(() -> timeTenths.set(timeTenths.get()-1));
            } catch (InterruptedException e) {
                running = false;
            }
        }
        if (timeSeconds == 0) {
            javafx.application.Platform.runLater(() -> completed.set(true));
        }
    }

    public void stopTimer() {
        running = false;
    }
    
    public javafx.beans.property.IntegerProperty timeTenthsProperty() {
        return timeTenths;
    }
    public int getTimeSeconds() {
            return timeSeconds;
    }
    public javafx.beans.property.BooleanProperty completedProperty() {
        return completed;
    }
}
