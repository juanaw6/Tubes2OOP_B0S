package tubes2oop_b0s;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SongPlayer extends Thread {

    public void run() {
        String path = "src/main/resources/songs/song1.mp3";
    
        //Instantiating Media class  
        Media media = new Media(new File(path).toURI().toString());
    
        //Instantiating MediaPlayer class   
        MediaPlayer mediaPlayer = new MediaPlayer(media);
    
        //by setting this property to true, the audio will be played   
            mediaPlayer.setAutoPlay(true);
    }
}
