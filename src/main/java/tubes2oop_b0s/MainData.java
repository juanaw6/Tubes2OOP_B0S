package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import net.bytebuddy.description.field.FieldDescription;
import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.deck.Deck;
import tubes2oop_b0s.field.Field;
import tubes2oop_b0s.loader.LoaderManager;
import tubes2oop_b0s.state.GameState;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.store.Store;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainData {
    private static volatile MainData instance = null;
    private ArrayList<Node> farmNodes;
    private ArrayList<Node> deckNodes;
    private ArrayList<Node> storeNodes;
    private String currentField;
    private int turn;

    // Private constructor to prevent instantiation from other classes
    private MainData() {
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        LoaderManager lm = LoaderManager.getInstance();
        lm.loadGameState("C:/Coding/Tubes/Tubes2OOP_B0S/src/main/resources/saves", "txt");
        turn = 0;
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

    public void NextTurn(ActionEvent event) {
        // change player
        GameState gs = GameState.getInstance();
        if (turn > 0) {
            gs.nextTurn();
            currentField = gs.getCurrentPlayer().getName();
        }
        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            Player player = gs.getCurrentPlayer();
            ArrayList<PlaceableCard> field = player.getFieldRef().getFieldCopy();
            ArrayList<Card> deck = player.getDeckRef().getActiveDeckRef();
            for (int i = 0; i < field.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = field.get(i) == null ? "" : field.get(i).getName();
                String path = field.get(i) == null ? "Empty" : field.get(i).getName();

                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, path + ".png", name, false);

                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = deck.get(i) == null ? "" : deck.get(i).getName();
                String path = deck.get(i) == null ? "Empty" : deck.get(i).getName();
                card.setId("deck-" + i);
                boolean isView = name.isEmpty();
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, path + ".png", name, isView);

                deckNodes.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        turn++;
        MainApplication.getInstance().showCardShufflePopup(event, getShuffleCards());
        int random = (int) (Math.random() * 6);
        if (random == 0) {
            MainController.getInstance().bearAttack();
        }
    }

    public ArrayList<String> getShuffleCards() {
        ArrayList<String> shuffleCards = new ArrayList<>();
        // shuffleCards.add("Card1,Accelerate.png");
        shuffleCards.add("Card2,Accelerate.png");
        shuffleCards.add("Card3,Accelerate.png");
        // shuffleCards.add("Card4,Accelerate.png");
        return shuffleCards;
    }

    public void SwapField() {
        // change player
        GameState gs = GameState.getInstance();
        currentField = gs.getEnemyPlayer().getName();

        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            Player player = gs.getEnemyPlayer();
            Player playerCurrent = gs.getCurrentPlayer();
            ArrayList<PlaceableCard> field = player.getFieldRef().getFieldCopy();
            ArrayList<Card> deck = playerCurrent.getDeckRef().getActiveDeckRef();
            for (int i = 0; i < field.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = field.get(i) == null ? "" : field.get(i).getName();
                String path = field.get(i) == null ? "Empty" : field.get(i).getName();

                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, path + ".png", name, true);

                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = deck.get(i) == null ? "" : deck.get(i).getName();
                String path = deck.get(i) == null ? "Empty" : deck.get(i).getName();
                boolean isView = !name.equals("Delay") && !name.equals("Destroy");
                card.setId("deck-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, path + ".png", name, isView);

                deckNodes.add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackSwapField() {
        // change player
        GameState gs = GameState.getInstance();
        currentField = gs.getCurrentPlayer().getName();
        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            Player player = gs.getCurrentPlayer();
            ArrayList<PlaceableCard> field = player.getFieldRef().getFieldCopy();
            ArrayList<Card> deck = player.getDeckRef().getActiveDeckRef();
            for (int i = 0; i < field.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = field.get(i) == null ? "" : field.get(i).getName();
                String path = field.get(i) == null ? "Empty" : field.get(i).getName();

                card.setId("farm-" + i);
                CardController controller = loader.getController();
                controller.setCardInfo("farm-" + i, path + ".png", name, false);

                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
                Node card = loader.load();

                String name = deck.get(i) == null ? "" : deck.get(i).getName();
                String path = deck.get(i) == null ? "Empty" : deck.get(i).getName();
                card.setId("deck-" + i);
                boolean isView = name.isEmpty();
                CardController controller = loader.getController();
                controller.setCardInfo("deck-" + i, path + ".png", name, isView);

                deckNodes.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setCurrentField(String currentField) {
        this.currentField = currentField;
    }

    public String getCurrentField() {
        return currentField;
    }
}
