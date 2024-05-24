package app;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BearAttackPopupController {

    @FXML
    private ImageView bearImage;

    @FXML
    private void handleDefend() {
        // Close the popup
        Stage stage = (Stage) bearImage.getScene().getWindow();
        stage.close();
    }
}
