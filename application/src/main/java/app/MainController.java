package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import app.state.GameState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainController {
    private static MainController instance;
    @FXML
    private Label welcomeText;

    @FXML
    private TilePane farm;
    @FXML
    private TilePane deck;

    @FXML
    private AnchorPane timerPlace;

    @FXML
    private Button farmButton;
    @FXML
    private Button pluginButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button shopButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button opponentButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label turn;
    @FXML
    private Label player2gold;
    @FXML
    private Label player1gold;
    @FXML
    private Label numDeck;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() {
        instance = this;
        reload();
    }

    public static MainController getInstance() {
        return instance;
    }

    public void reload() {
        MainData data = MainData.getInstance();
        GameState gs = GameState.getInstance();

        numDeck.setText(String.valueOf(gs.getCurrentPlayer().getDeckRef().getShuffledDeckSize()) + "/40");

        turn.setText(String.valueOf(data.getTurn()));
        farm.getChildren().clear();
        deck.getChildren().clear();
        ArrayList<Node> farmNodes = data.getFarmNodes();
        System.out.println(data.getTurn());
        for (int i = 0; i < farmNodes.size(); i++) {
            farm.getChildren().add(farmNodes.get(i));
        }
        ArrayList<Node> deckNodes = data.getDeckNodes();
        for (int i = 0; i < deckNodes.size(); i++) {
            deck.getChildren().add(deckNodes.get(i));
        }
        player1gold.setText(String.valueOf(gs.getPlayer1().getGulden()));
        player2gold.setText(String.valueOf(gs.getPlayer2().getGulden()));
    }

    public void bearAttack() {
        MainData data = MainData.getInstance();
        javafx.application.Platform.runLater(() -> {
            try {
                shopButton.setDisable(true);
                farmButton.setDisable(true);
                opponentButton.setDisable(true);
                loadButton.setDisable(true);
                pluginButton.setDisable(true);
                nextButton.setDisable(true);
                saveButton.setDisable(true);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/timer.fxml"));
                Node root = loader.load();
                TimerController controller = loader.getController();
                timerPlace.getChildren().add(root);
                data.setAttackCard();
                controller.timerCompletedProperty().addListener((obs, wasCompleted, isCompleted) -> {
                    if (isCompleted) {
                        data.finishAttackCard();
                        shopButton.setDisable(false);
                        farmButton.setDisable(false);
                        opponentButton.setDisable(false);
                        loadButton.setDisable(false);
                        pluginButton.setDisable(false);
                        nextButton.setDisable(false);
                        saveButton.setDisable(false);
                        timerPlace.getChildren().clear();// Call method to handle post-timer completion
                    }
                });
            } catch (Exception e) {
                System.err.println("Error loading FXML");
                e.printStackTrace();
            }
        });

    }

    @FXML
    protected void HandleOpenStore(ActionEvent event) {
        try {
            Parent mainRoot = FXMLLoader.load(getClass().getResource("/application/store.fxml"));
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
    protected void HandleNextTurn(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.NextTurn(event);
        reload();

        opponentButton.getStyleClass().clear();
        opponentButton.getStyleClass().add("button-default");

        farmButton.getStyleClass().clear();
        farmButton.getStyleClass().add("button-default-farm");
    }

    @FXML
    protected void HandleSwapField(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.SwapField();
        reload();

        opponentButton.getStyleClass().clear();
        opponentButton.getStyleClass().add("button-default-farm");

        farmButton.getStyleClass().clear();
        farmButton.getStyleClass().add("button-default");
    }

    @FXML
    protected void HandleBackSwapField(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.BackSwapField();
        reload();

        opponentButton.getStyleClass().clear();
        opponentButton.getStyleClass().add("button-default");

        farmButton.getStyleClass().clear();
        farmButton.getStyleClass().add("button-default-farm");
    }

    @FXML
    private void showSaveDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("/application/save_dialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/application/main.css")).toExternalForm());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showLoadDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("/application/load_dialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("/application/main.css").toExternalForm());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPluginDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("/application/plugin_dialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("/application/main.css").toExternalForm());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
