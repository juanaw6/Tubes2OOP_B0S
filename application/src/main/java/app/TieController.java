package app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TieController {

    @FXML
    private ImageView trophyImageView;

    @FXML
    private Text playerName;

    @FXML
    private Text score;

    public void setWinnerDetails(String name, int scoreValue) {
        playerName.setText(name);
        score.setText(String.valueOf(scoreValue));
    }

    @FXML
    private void handleFinish() {
        Stage stage = (Stage) trophyImageView.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}