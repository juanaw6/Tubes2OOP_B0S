package tubes2oop_b0s;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.event.ActionEvent;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private TilePane tilePane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void initialize() {
        for (int i = 0; i < 20; i++) {
            Button button = new Button("Card " + i);
            // Set an event handler on the button
            button.setOnAction(this::onHelloButtonClick2);  // Use method reference for cleaner code
            tilePane.getChildren().add(button);
        }
    }

    @FXML
    protected void onHelloButtonClick2(ActionEvent event) {
        Button newButton = new Button("Card cc");
        tilePane.getChildren().add(newButton);
    }
}
