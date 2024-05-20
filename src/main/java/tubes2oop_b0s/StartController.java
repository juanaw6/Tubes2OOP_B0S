package tubes2oop_b0s;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class StartController {
    

    @FXML
    protected void handleStartGame(ActionEvent event) {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("main.fxml"));
            Scene mainScene = new Scene(mainRoot, 1150, 900);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions possibly with a dialog or logging
        }
    }

    @FXML
    private void handleExit() {
        Platform.exit();
    }
}
