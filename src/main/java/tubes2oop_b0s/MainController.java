package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private TilePane farm;
    @FXML
    private TilePane deck;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() throws IOException {
        for (int i = 0; i < 20; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
            Node card = loader.load();
            
            CardController controller = loader.getController();
            controller.setCardInfo("card"+i, "item/Delay.png", "Delay");

            farm.getChildren().add(card);
        }
        for (int i = 0; i < 6; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
            Node card = loader.load();

            CardController controller = loader.getController();
            controller.setCardInfo("active-deck"+i, "item/Delay.png", "Delay");

            deck.getChildren().add(card);
        }
    }

    @FXML
    protected void onHelloButtonClick2(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        System.out.println("clicked button: " + clickedButton.getText());
        // welcomeText.setText(clickedButton.getText() + " clicked!");
    }

    @FXML
    protected void HandleOpenStore(ActionEvent event) {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("store.fxml"));
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

}
