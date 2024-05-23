
package tubes2oop_b0s;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import net.bytebuddy.asm.MemberSubstitution;
import net.bytebuddy.description.field.FieldDescription;
import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.crops.Crop;
import tubes2oop_b0s.card.effects.*;
import tubes2oop_b0s.deck.CardFactory;
import tubes2oop_b0s.deck.Deck;
import tubes2oop_b0s.field.Field;
import tubes2oop_b0s.loader.LoaderManager;
import tubes2oop_b0s.state.GameState;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.store.Store;

import javax.swing.*;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

public class MainData {
    private static volatile MainData instance = null;
    private ArrayList<Node> farmNodes;
    private ArrayList<Node> deckNodes;
    private ArrayList<Node> storeNodes;
    private ArrayList<Card> shuffleCards;
    private String currentField;
    private boolean bearAttack;
    private ArrayList<Integer> attacked;
    private int turn;
    private int expandCount;

    // Private constructor to prevent instantiation from other classes
    private MainData() {
        expandCount = 0;
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
        shuffleCards = new ArrayList<>();
        storeNodes = new ArrayList<>();
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        LoaderManager lm = LoaderManager.getInstance();
        lm.loadGameState("C:/Coding/Tubes/Tubes2OOP_B0S/src/main/resources/saves", "txt");
        turn = 0;
    }

    public void genRandomSubGrid(Integer rows, Integer cols) {
        // Initialize the larger grid with values
        int[][] grid = new int[rows][cols];
        int value = 1; // Start filling with 1
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = value++;
            }
        }

        int subgridRows = 2;
        int subgridCols = 3;

        Random random = new Random();
        int startRow = random.nextInt(rows - subgridRows + 1); // 0 or 1 or 2
        int startCol = random.nextInt(cols - subgridCols + 1); // 0 or 1 or 2

        attacked = new ArrayList<>();
        for (int i = 0; i < subgridRows; i++) {
            for (int j = 0; j < subgridCols; j++) {
                attacked.add(grid[startRow + i][startCol + j]);
            }
        }

    }

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
    // Public method to provide global access to the instance

    public void setAttackCard() {
        bearAttack = true;
        GameState gameState = GameState.getInstance();
        if (gameState.getCurrentPlayer().getFieldRef().getFieldRef().size() == 30){
            genRandomSubGrid(5, 6);
        }else {
            genRandomSubGrid(4, 5);
        }
        BackSwapField();
        MainController.getInstance().reload();
    }

    public void finishAttackCard() {
        GameState gs = GameState.getInstance();
        ArrayList<PlaceableCard> field = gs.getCurrentPlayer().getFieldRef().getFieldRef();
        boolean cancelAttack = false;
        for (int i = 0; i < field.size(); i++) {
            if (attacked.contains(i + 1)) {
                if (field.get(i) != null) {
                    if (field.get(i).hasTrap()) {
                        cancelAttack = true;
                    }
                }
            }
        }
        if (!cancelAttack) {
            for (int i = 0; i < field.size(); i++) {
                if (attacked.contains(i + 1)) {
                    if (field.get(i) != null) {
                        gs.getCurrentPlayer().getFieldRef().removeCard(i);
                    }
                }
            }
        }
        if (cancelAttack) {
            CardFactory factory = new CardFactory();
            Card db = factory.createCard("Beruang");
            gs.getCurrentPlayer().getDeckRef().addToActiveDeck(db);
        }
        bearAttack = false;
        attacked = new ArrayList<>();
        BackSwapField();
        MainController.getInstance().reload();
    }

    public void reloadStore() {
        Store store = Store.getInstance();
        storeNodes = new ArrayList<>();
        try {
            // ArrayList<ConsumableCard> consumableCards = store.getItemsRef();
            ArrayList<String> stringsUniq = store.getItemsUniqueStr();
            for (int i = 0; i < stringsUniq.size(); i++) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreCard.fxml"));
                Node card = loader.load();

                CardFactory factory = new CardFactory();
                ConsumableCard card1 = (ConsumableCard) factory.createCard(stringsUniq.get(i));

                StoreCardController controller = loader.getController();
                controller.setCardInfo("store-" + stringsUniq.get(i), stringsUniq.get(i) + ".png", stringsUniq.get(i),
                        card1.getPrice(), store.getQuantity(stringsUniq.get(i)));
                storeNodes.add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NextTurn(ActionEvent event) {
        // change player
        GameState gs = GameState.getInstance();
        if (expandCount > 0) {
            System.out.println("aaa");
            expandCount--;
            if (expandCount == 0) {
            System.out.println("aaabb");
                gs.getCurrentPlayer().getFieldRef().shrinkField();
            }
        }
        if (turn > 0) {
            if (turn >= 20) {
                int player1Gold = gs.getCurrentPlayer().getGulden();
                int player2Gold = gs.getEnemyPlayer().getGulden();
                if (player1Gold >= player2Gold) {
                    MainApplication.getInstance().showWinPopup(event);
                } else {
                    MainApplication.getInstance().showWinPopup(event);
                }
                return;
            }
            gs.nextTurn();
            gs.getCurrentPlayer().getFieldRef().incrementAllCropAge();
            gs.getCurrentPlayer().getFieldRef().activateAllEffects();
            gs.getEnemyPlayer().getFieldRef().incrementAllCropAge();
            gs.getEnemyPlayer().getFieldRef().activateAllEffects();
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

                if (field.get(i) instanceof Crop) {
                    Crop crop = (Crop) field.get(i);
                    if (crop.isReadyToHarvest()) {
                        path = path.replace("Biji ", "");
                    }
                }
                controller.setCardInfo("farm-" + i, path + ".png", name, false);
                if (bearAttack) {
                    if (attacked.contains(i + 1)) {
                        controller.setAttackedCard();
                    }
                }
                if(field.size() > 20) {
                    controller.setSmallCard();
                }
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
        for (int i = 0; i < gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().size(); i++) {
            if (gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(i) == null) {
                showShuffleCards(event);
                break;
            }
        }
        int random = (int) (Math.random() * 6);
        if (turn == 4) {
            MainController.getInstance().bearAttack();
        }
        turn++;

    }

    public void showShuffleCards(ActionEvent event) {
        MainApplication.getInstance().showCardShufflePopup(event, getShuffleCards());
    }

    public ArrayList<Card> getShuffleCards() {
        GameState gs = GameState.getInstance();
        Player player = gs.getCurrentPlayer();
        shuffleCards = player.getDeckRef().getRandomShuffled();
        // shuffleCards.add("Card1,Accelerate.png");
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
                if (field.get(i) instanceof Crop) {
                    Crop crop = (Crop) field.get(i);
                    if (crop.isReadyToHarvest()) {
                        path = path.replace("Biji ", "");
                    }
                }
                controller.setCardInfo("farm-" + i + "-enemy", path + ".png", name, true);
                if(field.size() > 20) {
                    controller.setSmallCard();
                }
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
                if (field.get(i) instanceof Crop) {
                    Crop crop = (Crop) field.get(i);
                    if (crop.isReadyToHarvest()) {
                        path = path.replace("Biji ", "");
                    }
                }
                controller.setCardInfo("farm-" + i, path + ".png", name, false);
                if (bearAttack) {
                    if (attacked.contains(i + 1)) {
                        controller.setAttackedCard();
                    }
                }
                if(field.size() > 20) {
                    controller.setSmallCard();
                }
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

    public void acceptShuffleCards() {
        GameState gs = GameState.getInstance();
        Player player = gs.getCurrentPlayer();
        player.getDeckRef().addToActiveDeck(shuffleCards);
        player.getDeckRef().removeFromShuffled(shuffleCards);

        BackSwapField();
        MainController.getInstance().reload();
    }

    public void onBuy(String name, ActionEvent event) {
        CardFactory cf = new CardFactory();
        Card card = cf.createCard(name);
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        if (card instanceof ConsumableCard) {
            int price = ((ConsumableCard) card).getPrice();
            Player current = gs.getCurrentPlayer();
            if (price > gs.getCurrentPlayer().getGulden()) {
                MainApplication.getInstance().showInvalidMovePopup(event);
            } else {
                int count = 0;
                for (int i = 0; i < gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().size(); i++) {
                    if (gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(i) == null) {
                        count++;
                    }
                }
                if (count == 0) {
                    MainApplication.getInstance().showInvalidMovePopup(event);
                } else {
                    current.setGulden(current.getGulden() - price);
                    store.removeItem(name);
                    gs.getCurrentPlayer().getDeckRef().addToActiveDeck(card);
                }
            }
        }
        BackSwapField();
        MainController.getInstance().reload();
        StoreController.getInstance().reload();

    }

    public void onSell(int id, ActionEvent event) {
        if (bearAttack) {
            MainApplication.getInstance().showInvalidMovePopup(event);
            return;
        }
        GameState gs = GameState.getInstance();
        String name = gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(id).getName();
        gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(id);
        CardFactory cf = new CardFactory();
        Card card = cf.createCard(name);
        Store store = Store.getInstance();
        if (card instanceof ConsumableCard) {
            int price = ((ConsumableCard) card).getPrice();
            Player current = gs.getCurrentPlayer();
            current.setGulden(current.getGulden() + price);
        }
        store.addItem(name);
        BackSwapField();
        MainController.getInstance().reload();
    }

    public void onHarvest(int id) {
        GameState gs = GameState.getInstance();
        PlaceableCard cs = gs.getCurrentPlayer().getFieldRef().getFieldRef().get(id);
        CardFactory cf = new CardFactory();
        if (cs instanceof Crop) {

            gs.getCurrentPlayer().getDeckRef().addToActiveDeck(cf.createCard(cs.getName().replace("Biji ", "")));
        } else if (cs instanceof Animal) {
            String name = "Daging " + cs.getName();
            if (cs.getName().equals("Hiu Darat")) {
                name = "Sirip Hiu";
            } else if (cs.getName().equals("Sapi")) {
                name = "Susu";
            } else if (cs.getName().equals("Ayam")) {
                name = "Telur";
            }

            gs.getCurrentPlayer().getDeckRef().addToActiveDeck(cf.createCard(name));
        }
        gs.getCurrentPlayer().getFieldRef().removeCard(id);
        BackSwapField();
        MainController.getInstance().reload();
    }

    public void DragDropCard(String source, String target, DragEvent event) {
        String[] sourceparts = source.split("-");
        String[] targetparts = target.split("-");

        GameState gs = GameState.getInstance();
        Player player = gs.getCurrentPlayer();

        int sourceIndex = Integer.parseInt(sourceparts[1]); //
        int targetIndex = Integer.parseInt(targetparts[1]); //

        if (sourceparts[0].equals(targetparts[0])) {
            if (sourceparts[0].equals("farm")) {
                PlaceableCard card = player.getFieldRef().getFieldRef().get(sourceIndex);
                if (player.getFieldRef().getFieldRef().get(targetIndex) == null) {
                    player.getFieldRef().removeCard(sourceIndex);
                    player.getFieldRef().addPlaceableCard(targetIndex, card);
                } else {
                    MainApplication.getInstance().showInvalidMovePopup(event);
                }

            } else if (sourceparts[0].equals("deck")) {
                Card card = player.getDeckRef().getActiveDeckRef().get(sourceIndex);
                if (player.getDeckRef().getActiveDeckRef().get(targetIndex) == null) {
                    player.getDeckRef().removeFromActiveDeck(sourceIndex);
                    player.getDeckRef().addToActiveDeck(targetIndex, card);
                } else {
                    MainApplication.getInstance().showInvalidMovePopup(event);
                }
            }
            BackSwapField();
        } else {
            PlaceableCard targetCard = player.getFieldRef().getFieldRef().get(targetIndex);
            Card sourceCard = player.getDeckRef().getActiveDeckRef().get(sourceIndex);
            if (!currentField.equals(gs.getCurrentPlayer().getName())) {
                PlaceableCard targetCardEnemy = gs.getEnemyPlayer().getFieldRef().getFieldRef().get(targetIndex);
                if (targetCardEnemy != null) {
                    if (sourceCard instanceof Destroy) {
                        if (!targetCardEnemy.isProtected()) {
                            gs.getEnemyPlayer().getFieldRef().removeCard(targetIndex);
                        }
                        gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                        SwapField();
                    } else if (sourceCard instanceof Delay) {
                        ((Delay) sourceCard).use(targetCardEnemy);
                        targetCardEnemy.addEffect(sourceCard.getName());
                        gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                        SwapField();
                    }
                } else {
                    MainApplication.getInstance().showInvalidMovePopup(event);
                }
            } else {
                if (sourceCard instanceof Layout) {
                    gs.getCurrentPlayer().getFieldRef().expandField();
                    gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                    expandCount = 5;
                    BackSwapField();
                }
                else if ((targetCard != null)) {
                    // aksi dalam ladang sendiri
                    if (sourceCard instanceof ConsumableCard && targetCard instanceof Animal) {
                        // consume something
                        try {
                            System.out.println("try eat");
                            ((Animal) targetCard).consume((ConsumableCard) sourceCard);
                            player.getDeckRef().removeFromActiveDeck(sourceCard);
                            BackSwapField();
                            System.out.println("success eat");
                        } catch (Exception e) {
                            // create popup invalid action
                            e.printStackTrace();
                            MainApplication.getInstance().showInvalidMovePopup(event);
                        }
                    } else if (sourceCard instanceof EffectCard) {
                        if (sourceCard instanceof Accelerate) {
                            ((Accelerate) sourceCard).use(targetCard);
                            targetCard.addEffect(sourceCard.getName());
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            BackSwapField();
                        } else if (sourceCard instanceof InstantHarvest) {
                            ConsumableCard consumableCard = targetCard.harvest();
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            gs.getCurrentPlayer().getDeckRef().addToActiveDeck(consumableCard);
                            gs.getCurrentPlayer().getFieldRef().removeCard(targetIndex);
                            BackSwapField();
                        } else if (!(sourceCard instanceof Destroy) && !(sourceCard instanceof Delay)) {
                            targetCard.addEffect(sourceCard.getName());
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            BackSwapField();
                        } else {
                            MainApplication.getInstance().showInvalidMovePopup(event);
                        }
                    } else {
                        // menaruh bukan efek bukan makanan ke ladang isi itu nggk boleh
                        MainApplication.getInstance().showInvalidMovePopup(event);
                    }
                } else if (sourceCard instanceof PlaceableCard) {
                    System.out.println(sourceCard.getName());
                    gs.getCurrentPlayer().getFieldRef().addPlaceableCard(targetIndex, (PlaceableCard) sourceCard);
                    gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);

                    BackSwapField();
                } else {
                    // give pop up invalid act
                    System.out.println(sourceCard.getName() + "invalid act");
                    MainApplication.getInstance().showInvalidMovePopup(event);
                }
            }
        }
        MainController.getInstance().reload();
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

    public boolean isBearAttack() {
        return bearAttack;
    }
}
