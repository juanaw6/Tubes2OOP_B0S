package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class WinPopUpController {

    @FXML
    private ImageView trophyImageView;

    @FXML
    private void handleFinish() {
        Stage stage = (Stage) trophyImageView.getScene().getWindow();
        stage.close();
    }
}
