package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import net.bytebuddy.description.field.FieldDescription;
import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.effects.*;
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
    private ArrayList<Card> shuffleCards;
    private String currentField;
    private int turn;

    // Private constructor to prevent instantiation from other classes
    private MainData() {
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
        shuffleCards = new ArrayList<>();
        storeNodes =  new ArrayList<>();
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
    
    public void reloadStore(){
        
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
        showShuffleCards(event);
        int random = (int) (Math.random() * 6);
        if (random == 0 && turn > 0) {
            MainController.getInstance().bearAttack();
        }
        turn++;
        
    }
    
    public void showShuffleCards(ActionEvent event){
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
    
    public void acceptShuffleCards(){
        GameState gs = GameState.getInstance();
        Player player = gs.getCurrentPlayer();
        player.getDeckRef().addToActiveDeck(shuffleCards);
        player.getDeckRef().removeFromShuffled(shuffleCards);
        
        BackSwapField();
        MainController.getInstance().reload();
    }

    
    public void DragDropCard(String source, String target){
        String[] sourceparts = source.split("-");
        String[] targetparts = target.split("-");
        
        GameState gs = GameState.getInstance();
        Player player = gs.getCurrentPlayer();

        int sourceIndex = Integer.parseInt(sourceparts[1]);  // 
        int targetIndex = Integer.parseInt(targetparts[1]);  //
        
        if (sourceparts[0].equals(targetparts[0])) {
            if (sourceparts[0].equals("farm")){
                PlaceableCard card = player.getFieldRef().getFieldRef().get(sourceIndex);
                player.getFieldRef().removeCard(sourceIndex);
                player.getFieldRef().addPlaceableCard(targetIndex, card);
                
            }else if (sourceparts[0].equals("deck")){
                Card card = player.getDeckRef().getActiveDeckRef().get(sourceIndex);
                player.getDeckRef().removeFromActiveDeck(sourceIndex);
                player.getDeckRef().addToActiveDeck(targetIndex, card);
            }
            BackSwapField();
        }else{
            PlaceableCard targetCard = player.getFieldRef().getFieldRef().get(targetIndex);
            Card sourceCard = player.getDeckRef().getActiveDeckRef().get(sourceIndex);
            if (!currentField.equals(gs.getCurrentPlayer().getName())) {
                PlaceableCard targetCardEnemy = gs.getEnemyPlayer().getFieldRef().getFieldRef().get(targetIndex);
                if (targetCardEnemy != null) {
                    if (sourceCard instanceof Destroy) {
                        gs.getEnemyPlayer().getFieldRef().removeCard(targetIndex);
                        gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                        SwapField();
                    } else if (sourceCard instanceof Delay) {
                        ((Delay) sourceCard).use(targetCardEnemy);
                        targetCardEnemy.addEffect(sourceCard.getName());
                        gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                        SwapField();
                    }
                }
            }else {
                if ((targetCard != null)) {
                    //                aksi dalam ladang sendiri
                    if (sourceCard instanceof ConsumableCard && targetCard instanceof Animal) {
//     consume something
                        try {
                            System.out.println("try eat");
                            ((Animal) targetCard).consume((ConsumableCard) sourceCard);
                            player.getDeckRef().removeFromActiveDeck(sourceCard);
                            System.out.println("success eat");
                        } catch (Exception e) {
//                            create popup invalid action
                            e.printStackTrace();
                        }
                    } else if (sourceCard instanceof EffectCard) {
                        if (sourceCard instanceof Accelerate) {
                            ((Accelerate) sourceCard).use(targetCard);
                            targetCard.addEffect(sourceCard.getName());
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            BackSwapField();
                        } else if (sourceCard instanceof InstantHarvest) {
                            ConsumableCard consumableCard = targetCard.harvest();
                            gs.getCurrentPlayer().getDeckRef().addToActiveDeck(consumableCard);
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            gs.getCurrentPlayer().getFieldRef().removeCard(targetIndex);
                            BackSwapField();
                        } else if (!(sourceCard instanceof Destroy) && !(sourceCard instanceof Delay)){
                            targetCard.addEffect(sourceCard.getName());
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            BackSwapField();
                        }
                    }
                } else if (sourceCard instanceof PlaceableCard) {
                    System.out.println(sourceCard.getName());
                    gs.getCurrentPlayer().getFieldRef().addPlaceableCard(targetIndex, (PlaceableCard) sourceCard);
                    gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);

                    BackSwapField();
                } else {
//                give pop up invalid act
                    System.out.println(sourceCard.getName() + "invalid act");
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
}
