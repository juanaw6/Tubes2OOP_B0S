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
import tubes2oop_b0s.card.Card;

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
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public void showCardShufflePopup(ActionEvent event, ArrayList<Card> shuffleCards) {
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
        SongPlayer songPlayer = new SongPlayer("song1.mp3", 2);
        songPlayer.play();
        launch(args);
    }
}
