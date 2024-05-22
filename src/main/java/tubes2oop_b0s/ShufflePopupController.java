package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ShufflePopupController {

    @FXML
    private VBox card1;
    @FXML
    private VBox card2;
    @FXML
    private VBox card3;
    @FXML
    private VBox card4;

    @FXML
    public void initialize() {
        try {
            initializeCard(card1, "Card1", "Accelerate.png");
            initializeCard(card2, "Card2", "Accelerate.png");
            initializeCard(card3, "Card3", "Accelerate.png");
            initializeCard(card4, "Card4", "Accelerate.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCard(VBox cardBox, String cardName, String imageName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
        VBox card = loader.load();
        CardController controller = loader.getController();
        controller.setCardInfo(cardName, imageName, cardName, true);
        cardBox.getChildren().add(card);
    }

    @FXML
    private void handleReshuffle() {
        // Implement reshuffle logic, e.g., change card images or names
        System.out.println("Reshuffle button clicked");
    }

    @FXML
    private void handleAccept() {
        // Implement accept logic, e.g., close the popup and use selected cards
        Stage stage = (Stage) card1.getScene().getWindow();
        stage.close();
    }
}
