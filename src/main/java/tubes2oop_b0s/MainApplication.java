package tubes2oop_b0s;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;

import java.util.ArrayList;

public class MainApplication extends Application {
    private static MainApplication instance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainData mainData = MainData.getInstance();
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Welcome to the Game!");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setResizable(false);
        primaryStage.show();

        // Load multiple cards - example task after the stage is shown

        loadCards();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    private void loadCards() {
        // for create all element card for first time
        try {
            MainData mainData = MainData.getInstance();
            ArrayList<Node> farmNodes = new ArrayList<>();
            ArrayList<Node> deckNodes = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, "Empty.png", "", false);

                farmNodes.add(card);
            }
            for (int i = 0; i < 6; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("deck-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, "Empty.png", "", false);

                deckNodes.add(card);
            }
            mainData.setFarmNodes(farmNodes);
            mainData.setDeckNodes(deckNodes);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showCardShufflePopup(ActionEvent event, ArrayList<String> shuffleCards) {
        javafx.application.Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Shuffle.fxml"));
                Parent root = loader.load();

                ShufflePopupController controller = loader.getController();
                controller.setShuffleCards(shuffleCards);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.setTitle("Card Shuffle");
                popupStage.setScene(new Scene(root, 600, 400));
                popupStage.showAndWait();
            } catch (Exception e) {
                System.err.println("Error loading FXML or setting up the popup");
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        SongPlayer songPlayer = new SongPlayer();
        songPlayer.start();
        launch(args);
    }
}
