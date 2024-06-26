package app;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;

    @FXML
    protected void handleStartGame(ActionEvent event) {
        try {
            MainData mainData = MainData.getInstance();
            mainData.NextTurn(event);
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/application/main.fxml"));
            Scene mainScene = new Scene(mainRoot, 1150, 900);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.setResizable(false);

            // Center the window on the screen
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((screenBounds.getWidth() - mainScene.getWidth()) / 2);
            window.setY((screenBounds.getHeight() - mainScene.getHeight()) / 2);

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
