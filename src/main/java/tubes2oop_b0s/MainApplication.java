package tubes2oop_b0s;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize singleton data
        MainData mainData = MainData.getInstance();

        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Welcome to the Game!");
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setResizable(false);
        primaryStage.show();

        // Load multiple cards - example task after the stage is shown
        loadCards();
    }

    private void loadCards() {
        try {
            MainData mainData = MainData.getInstance();
            ArrayList<Node> farmNodes = new ArrayList<>();
            ArrayList<Node> deckNodes = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it appropriately
                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-"+i, "Delay.png", "Delay", false);
                
                farmNodes.add(card);
            }
            for (int i = 0; i < 6; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it appropriately
                card.setId("deck-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("deck-"+i, "Accelerate.png", "Accelerate", false);
                
                deckNodes.add(card);
            }
            mainData.setFarmNodes(farmNodes);
            mainData.setDeckNodes(deckNodes);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        SongPlayer songPlayer =  new SongPlayer();
        songPlayer.start();
        launch(args);
    }
}
