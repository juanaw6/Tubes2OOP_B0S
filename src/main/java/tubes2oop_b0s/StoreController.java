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
import java.util.ArrayList;

public class StoreController {
    private static StoreController instance;

    public static StoreController getInstance() {
        return instance;
    }
    
    @FXML
    private TilePane store;

    public void initialize() throws IOException {
        instance = this;
        MainData mainData = MainData.getInstance();
        mainData.reloadStore();
        ArrayList<Node> nodes = mainData.getStoreNodes();
        for (int i = 0; i < nodes.size(); i++) {
            store.getChildren().add(nodes.get(i));
        }
    }
    
    public void reload(){
        MainData mainData = MainData.getInstance();
        store.getChildren().clear();
        mainData.reloadStore();
        ArrayList<Node> nodes = mainData.getStoreNodes();
        for (int i = 0; i < nodes.size(); i++) {
            store.getChildren().add(nodes.get(i));
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
