package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import app.card.Card;
import app.state.GameState;

import java.util.ArrayList;

public class MainApplication extends Application {
    private static MainApplication instance;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainData mainData = MainData.getInstance();
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("/application/start.fxml"));
        primaryStage.setTitle("Welcome to the Game!");
        primaryStage.setScene(new Scene(root, 1440, 900));
        primaryStage.setResizable(false);
        primaryStage.show();

        SongPlayer songPlayer = new SongPlayer("song1.mp3", 20);
        songPlayer.play();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public void showCardShufflePopup(ActionEvent event, ArrayList<Card> shuffleCards) {
        javafx.application.Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/shuffle.fxml"));
                Parent root = loader.load();

                ShufflePopupController controller = loader.getController();
                controller.setShuffleCards(shuffleCards);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.setTitle("Card Shuffle");
                popupStage.setScene(new Scene(root, 400, 400));
                popupStage.showAndWait();
                
            } catch (Exception e) {
                System.err.println("Error loading FXML or setting up the popup");
                e.printStackTrace();
            }
        });
    }

    public void showBearAttackPopup(ActionEvent event) {
        javafx.application.Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/bear_popup.fxml"));
                Parent root = loader.load();

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.setTitle("Bear Attack");
                popupStage.setScene(new Scene(root, 400, 500));
                popupStage.showAndWait();
            } catch (Exception e) {
                System.err.println("Error loading FXML");
                e.printStackTrace();
            }
        });
    }

    public void showWinPopup(ActionEvent event, String playerName, int score) {
        javafx.application.Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/win.fxml"));
                Parent root = loader.load();
                WinPopUpController controller = loader.getController();
                controller.setWinnerDetails(playerName, score);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.setTitle("You Win!");
                popupStage.setScene(new Scene(root, 500, 500));
                popupStage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showTiePopup(ActionEvent event, String playerName, int score) {
        javafx.application.Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/tie.fxml"));
                Parent root = loader.load();
                TieController controller = loader.getController();
                controller.setWinnerDetails(playerName, score);

                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.initStyle(StageStyle.UNDECORATED);
                popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                popupStage.setTitle("Tie!");
                popupStage.setScene(new Scene(root, 500, 500));
                popupStage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void showInvalidMovePopup(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/invalid.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.setTitle("Invalid Move");
            popupStage.setScene(new Scene(root, 450, 100));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showInvalidMovePopup(DragEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/invalid.fxml"));
            Parent root = loader.load();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            popupStage.setTitle("Invalid Move");
            popupStage.setScene(new Scene(root, 450, 100));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
