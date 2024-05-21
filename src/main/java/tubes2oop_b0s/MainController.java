package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private TilePane farm;
    @FXML
    private TilePane deck;
    
   @FXML
    private AnchorPane timerPlace;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() throws IOException {
        
        reload();
    }

    private void reload() throws IOException{
        farm.getChildren().clear();
        deck.getChildren().clear();
        MainData data = MainData.getInstance();
        if (data.getTurn() ==3){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Timer.fxml"));
            Node root = loader.load();
            TimerController controller = loader.getController();
            timerPlace.getChildren().add(root);
            controller.timerCompletedProperty().addListener((obs, wasCompleted, isCompleted) -> {
                if (isCompleted) {
                    timerPlace.getChildren().clear();// Call method to handle post-timer completion
                }
            });
        }
        ArrayList<Node> farmNodes = data.getFarmNodes();
        System.out.println(data.getTurn());
        for (int i = 0; i < farmNodes.size(); i++) {
            farm.getChildren().add(farmNodes.get(i));
        }
        ArrayList<Node> deckNodes = data.getDeckNodes();
        for (int i = 0; i < deckNodes.size(); i++) {
            deck.getChildren().add(deckNodes.get(i));
        }
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

    @FXML
    protected void HandleNextTurn(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.NextTurn();
        reload();
    }

    @FXML
    protected void HandleSwapField(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.SwapField();
        reload();
    }

    @FXML
    protected void HandleBackSwapField(ActionEvent event) throws IOException {
        MainData mainData = MainData.getInstance();
        mainData.BackSwapField();
        reload();
    }

    @FXML
    private void showSaveDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("SaveDialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showLoadDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("LoadDialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPluginDialog(ActionEvent event) {
        try {
            VBox dialogContent = FXMLLoader.load(getClass().getResource("PluginDialog.fxml"));
            Dialog<Void> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialog.getDialogPane().setContent(dialogContent);
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
