package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tubes2oop_b0s.card.Card;

import java.io.IOException;
import java.util.ArrayList;

public class ShufflePopupController {

    @FXML
    private GridPane cardGrid;

    public void setShuffleCards(ArrayList<Card> shuffleCards) {
        cardGrid.getChildren().clear();
        int numCards = shuffleCards.size();

        if (numCards == 1) {
            addCardToGrid(shuffleCards.get(0), 0, 0);
        } else if (numCards == 2) {
            addCardToGrid(shuffleCards.get(0), 0, 0);
            addCardToGrid(shuffleCards.get(1), 1, 0);
        } else if (numCards == 3) {
            addCardToGrid(shuffleCards.get(0), 0, 0);
            addCardToGrid(shuffleCards.get(1), 1, 0);
            addCardToGrid(shuffleCards.get(2), 0, 1, 2); // Span two columns
        } else if (numCards == 4) {
            addCardToGrid(shuffleCards.get(0), 0, 0);
            addCardToGrid(shuffleCards.get(1), 1, 0);
            addCardToGrid(shuffleCards.get(2), 0, 1);
            addCardToGrid(shuffleCards.get(3), 1, 1);
        } else {
            int rows = (int) Math.ceil(Math.sqrt(numCards));
            int cols = (int) Math.ceil((double) numCards / rows);

            for (int i = 0; i < numCards; i++) {
                addCardToGrid(shuffleCards.get(i), i % cols, i / cols);
            }
        }
    }

    private void addCardToGrid(Card cardData, int colIndex, int rowIndex) {
        addCardToGrid(cardData, colIndex, rowIndex, 1);
    }

    private void addCardToGrid(Card cardData, int colIndex, int rowIndex, int colSpan) {
        String cardName = cardData.getName();
        String imageName = cardData.getName() + ".png";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("card.fxml"));
            VBox card = loader.load();
            CardController controller = loader.getController();
            controller.setCardInfo(cardName, imageName, cardName, true);
            cardGrid.add(card, colIndex, rowIndex, colSpan, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReshuffle(ActionEvent event) {
        Stage stage = (Stage) cardGrid.getScene().getWindow();
        stage.close();
        MainData mainData = MainData.getInstance();
        mainData.showShuffleCards(event);
        System.out.println("Reshuffle button clicked");
    }

    @FXML
    private void handleAccept(ActionEvent event) {
        MainData mainData = MainData.getInstance();
        mainData.acceptShuffleCards();
        Stage stage = (Stage) cardGrid.getScene().getWindow();
        stage.close();
    }
}
