package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;

public class StoreController {
    @FXML
    private TilePane store;

    public void initialize() throws IOException {
        for (int i = 0; i < 20; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
            Node card = loader.load();

            CardController controller = loader.getController();
            controller.setCardInfo("store-"+i, "Delay.png", "Delay", false);
            store.getChildren().add(card);
        }
       
    }

    @FXML
    protected void HandleBackToMain(ActionEvent event) {
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
}
