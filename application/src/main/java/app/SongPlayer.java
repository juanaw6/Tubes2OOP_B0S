package app;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SongPlayer {
    private MediaPlayer mediaPlayer;

    public SongPlayer(String filePath, double volume) {
        // Initialize Media and MediaPlayer on JavaFX Thread
        Platform.runLater(() -> {
            Media media = new Media(getClass().getResource("/songs/" + filePath).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(volume);
        });
    }

    public void play() {
        Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(10);
                mediaPlayer.play();
            }
        });
    }

    public void stop() {
        Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });
    }
}
