package tubes2oop_b0s;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import tubes2oop_b0s.state.Player;

import java.io.IOException;
import java.util.ArrayList;

public class MainData {
    private static volatile MainData instance = null;
    private ArrayList<Node> farmNodes;
    private ArrayList<Node> deckNodes;
    private ArrayList<Node> storeNodes;
    private String currentPLayer;
    private String currentDeck;
    private int turn;
    private Player player1;
    private Player player2;

    // Private constructor to prevent instantiation from other classes
    private MainData() {
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
//         player1 = new Player("Player 1");
//         player2 = new Player("Player 2");
        currentPLayer = "Player 1";
        currentDeck = "Player 1";
    }

    // Public method to provide global access to the instance
    public static MainData getInstance() {
        if (instance == null) { // First check (no locking)
            synchronized (MainData.class) {
                if (instance == null) { // Second check (with locking)
                    instance = new MainData();
                }
            }
        }
        return instance;
    }

    public void NextTurn() {
        // change player
        if (currentPLayer.equals("Player 1")) {
            currentPLayer = "Player 2";
            currentDeck = "Player 2";
        } else if (currentPLayer.equals("Player 2")) {
            currentPLayer = "Player 1";
            currentDeck = "Player 1";
        }

        // change data deck and farm
        try {
            if (currentPLayer.equals("Player 1")) {
                farmNodes = new ArrayList<>();
                deckNodes = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                    Node card = loader.load(); // Assuming you'll use it or add to some container
                    // Here you should add 'card' to a visible UI container or manage it
                    // appropriately
                    card.setId("farm-" + i);
                    CardController controller = loader.getController();
                    controller.setCardInfo("farm-" + i, "Empty.png", "Delay", false);

                    farmNodes.add(card);
                }
                for (int i = 0; i < 6; i++) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                    Node card = loader.load(); // Assuming you'll use it or add to some container
                    // Here you should add 'card' to a visible UI container or manage it
                    // appropriately
                    card.setId("deck-" + i);
                    CardController controller = loader.getController();
                    controller.setCardInfo("deck-" + i, "Empty.png", "Accelerate", false);

                    deckNodes.add(card);
                }
            } else {
                farmNodes = new ArrayList<>();
                deckNodes = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                    Node card = loader.load(); // Assuming you'll use it or add to some container
                    // Here you should add 'card' to a visible UI container or manage it
                    // appropriately
                    card.setId("farm-" + i);
                    CardController controller = loader.getController();
                    controller.setCardInfo("farm-" + i, "Empty.png", "Delay", false);

                    farmNodes.add(card);
                }
                for (int i = 0; i < 6; i++) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                    Node card = loader.load(); // Assuming you'll use it or add to some container
                    // Here you should add 'card' to a visible UI container or manage it
                    // appropriately
                    card.setId("deck-" + i);
                    CardController controller = loader.getController();
                    controller.setCardInfo("deck-" + i, "Empty.png", "Accelerate", false);

                    deckNodes.add(card);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        turn++;
        MainApplication.getInstance().showCardShufflePopup();
        int random = (int) (Math.random() * 6);
        if (random ==3){
                MainController.getInstance().bearAttack();
        }
    }

    public void SwapField() {
        // change player
        if (currentPLayer.equals("Player 1")) {
            currentDeck = "Player 2";
        } else {
            currentDeck = "Player 1";
        }

        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, "Delay.png", "Delay", true);

                farmNodes.add(card);
            }

            // use old data only
            for (int i = 0; i < 6; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("deck-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, "Accelerate.png", "Accelerate", true);

                deckNodes.add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackSwapField() {
        // change player
        currentDeck = currentPLayer;
        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, "Accelerate.png", "Delay", false);

                farmNodes.add(card);
            }

            // use old data only
            for (int i = 0; i < 6; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load(); // Assuming you'll use it or add to some container
                // Here you should add 'card' to a visible UI container or manage it
                // appropriately
                card.setId("deck-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, "Accelerate.png", "Accelerate", false);

                deckNodes.add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentPLayer() {
        return currentPLayer;
    }

    public void setCurrentDeck(String currentDeck) {
        this.currentDeck = currentDeck;
    }

    public String getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentPLayer(String currentPLayer) {
        this.currentPLayer = currentPLayer;
    }

    public ArrayList<Node> getStoreNodes() {
        return storeNodes;
    }

    public void setStoreNodes(ArrayList<Node> storeNodes) {
        this.storeNodes = storeNodes;
    }

    public ArrayList<Node> getDeckNodes() {
        return deckNodes;
    }

    public void setDeckNodes(ArrayList<Node> deckNodes) {
        this.deckNodes = deckNodes;
    }

    public void setFarmNodes(ArrayList<Node> farmNodes) {
        this.farmNodes = farmNodes;
    }

    public ArrayList<Node> getFarmNodes() {
        return farmNodes;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
