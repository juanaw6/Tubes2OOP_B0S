
package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import app.card.Card;
import app.card.ConsumableCard;
import app.card.EffectCard;
import app.card.PlaceableCard;
import app.card.animals.Animal;
import app.card.crops.Crop;
import app.card.effects.*;
import app.deck.CardFactory;
import app.state.GameState;
import app.state.Player;
import app.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, Integer> expandCount = new HashMap<>();

    // Private constructor to prevent instantiation from other classes
    private MainData() {
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
        shuffleCards = new ArrayList<>();
        storeNodes = new ArrayList<>();
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        expandCount.put(gs.getPlayer1().getName(), 0);
        expandCount.put(gs.getPlayer2().getName(), 0);
        turn = 0;
    }

    public void UpdateMainData() {
        farmNodes = new ArrayList<>(20);
        deckNodes = new ArrayList<>(6);
        shuffleCards = new ArrayList<>();
        storeNodes = new ArrayList<>();
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        expandCount.put(gs.getPlayer1().getName(), 0);
        expandCount.put(gs.getPlayer2().getName(), 0);
        turn = 0;
    }

    public void genRandomSubGrid(Integer rows, Integer cols) {
        // Initialize the larger grid with values
        Random random = new Random();
        int subgridArea = random.nextInt(1, 7);
        List<List<Integer>> positions = new ArrayList<>();
        attacked = new ArrayList<>();

        switch (subgridArea) {
            case 1: // 1x1 subgrid
                positions.add(List.of(random.nextInt(rows), random.nextInt(cols)));
                break;
            case 2: // 1x2 or 2x1 subgrid
                if (random.nextBoolean()) { // 1 x 2
                    int row2 = random.nextInt(0, rows);
                    int col2 = random.nextInt(0, cols - 1); // [0..3]
                    positions.add(List.of(row2, col2));
                    positions.add(List.of(row2, col2 + 1));
                } else {
                    int row2 = random.nextInt(0, rows - 1); // [0..2]
                    int col2 = random.nextInt(0, cols);
                    positions.add(List.of(row2, col2));
                    positions.add(List.of(row2 + 1, col2));
                }
                break;
            case 3: // 1x3 or 3x1 subgrid
                if (random.nextBoolean()) { // 1x3
                    int row3 = random.nextInt(0, rows);
                    int col3 = random.nextInt(0, cols - 2); // [0..2]
                    positions.add(List.of(row3, col3));
                    positions.add(List.of(row3, col3 + 1));
                    positions.add(List.of(row3, col3 + 2));
                } else { // 3 x 1
                    int row3 = random.nextInt(0, rows - 2); // [0..1]
                    int col3 = random.nextInt(0, cols);
                    positions.add(List.of(row3, col3));
                    positions.add(List.of(row3 + 1, col3));
                    positions.add(List.of(row3 + 2, col3));
                }
                break;
            case 4: // 2x2 subgrid
                int row4 = random.nextInt(0, rows - 1); // [0..2]
                int col4 = random.nextInt(0, cols - 1); // [0..3]
                positions.add(List.of(row4, col4));
                positions.add(List.of(row4, col4 + 1));
                positions.add(List.of(row4 + 1, col4));
                positions.add(List.of(row4 + 1, col4 + 1));
                break;
            case 5: // 1x5 or 5x1 subgrid
                if (random.nextBoolean()) {
                    int row5 = random.nextInt(0, rows); // 1x5
                    for (int i = 0; i < 5; i++) {
                        positions.add(List.of(row5, i));
                    }
                } else {
                    int col5 = random.nextInt(0, cols); // 4x1
                    for (int i = 0; i < 4; i++) {
                        positions.add(List.of(i, col5));
                    }
                }
                break;
            case 6: // 2x3 or 3x2 subgrid batas aman row[0..3] col[0..4]
                if (random.nextBoolean()) { // 2x3
                    int row6 = random.nextInt(0, rows - 2); // row6[0..2]
                    int col6 = random.nextInt(0, cols - 3); // col6[0..2]
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 3; j++) {
                            positions.add(List.of(row6 + i, col6 + j));
                        }
                    }
                } else { // 3x2
                    int row6 = random.nextInt(0, rows - 3); // row6[0..1]
                    int col6 = random.nextInt(0, cols - 2); // col6[0..3]
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 2; j++) {
                            positions.add(List.of(row6 + i, col6 + j));
                        }
                    }
                }
                break;
        }
        for (int i = 0; i < positions.size(); i++) {
            attacked.add((positions.get(i).get(0) * cols) + positions.get(i).get(1)+1);
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
        if (gameState.getCurrentPlayer().getFieldRef().getFieldRef().size() == 30) {
            genRandomSubGrid(5, 6);
        } else {
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
                    if (field.get(i) != null && !field.get(i).isProtected()) {
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

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/store_card.fxml"));
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
        int expandCount1 = expandCount.get(gs.getPlayer1().getName());
        int expandCount2 = expandCount.get(gs.getPlayer2().getName());
        if (expandCount1 > 0) {
            expandCount1--;
            expandCount.put(gs.getPlayer1().getName(), expandCount1);
            if (expandCount1 == 0) {
                gs.getPlayer1().getFieldRef().shrinkField();
            }
        }
        if (expandCount2 > 0) {
            expandCount2--;
            expandCount.put(gs.getPlayer2().getName(), expandCount2);
            if (expandCount2 == 0) {
                gs.getPlayer2().getFieldRef().shrinkField();
            }
        }
        if (turn > 0) {
            if (turn >= 20) {
                int player1Gold = gs.getCurrentPlayer().getGulden();
                int player2Gold = gs.getEnemyPlayer().getGulden();
                if (player1Gold == player2Gold) {
                    MainApplication.getInstance().showTiePopup(event, "TIE", 0);
                } else if (player1Gold > player2Gold) {
                    MainApplication.getInstance().showWinPopup(event, gs.getCurrentPlayer().getName(),
                            gs.getCurrentPlayer().getGulden());
                } else {
                    MainApplication.getInstance().showWinPopup(event, gs.getEnemyPlayer().getName(),
                            gs.getEnemyPlayer().getGulden());
                }
                return;
            }
            gs.nextTurn();
            gs.getCurrentPlayer().getFieldRef().incrementAllCropAge();
            // gs.getCurrentPlayer().getFieldRef().activateAllEffects();
            gs.getEnemyPlayer().getFieldRef().incrementAllCropAge();
            // gs.getEnemyPlayer().getFieldRef().activateAllEffects();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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
                if (field.size() > 20) {
                    controller.setSmallCard();
                }
                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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
        boolean getShuffle = false;
        for (int i = 0; i < gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().size(); i++) {
            if (gs.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(i) == null) {
                showShuffleCards(event);
                getShuffle = true;
                break;
            }
        }
        if (!getShuffle) {
            int random = (int) (Math.random() * 5);
            if (random == 0 && gs.getTurn() > 1) {
                MainApplication.getInstance().showBearAttackPopup(event);
                MainController.getInstance().bearAttack();
            }
        }

        turn++;

    }

    public void showShuffleCards(ActionEvent event) {
        MainApplication.getInstance().showCardShufflePopup(event, getShuffleCards());
    }

    public void showBearAttack(ActionEvent event) {
        GameState gs = GameState.getInstance();
        int random = (int) (Math.random() * 5);
        if (random == 0 && gs.getTurn() > 1) {
            MainApplication.getInstance().showBearAttackPopup(event);
            MainController.getInstance().bearAttack();
        }
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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
                if (field.size() > 20) {
                    controller.setSmallCard();
                }
                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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
        turn = gs.getTurn();
        // change data deck and farm
        try {
            farmNodes = new ArrayList<>();
            deckNodes = new ArrayList<>();
            Player player = gs.getCurrentPlayer();
            ArrayList<PlaceableCard> field = player.getFieldRef().getFieldCopy();
            ArrayList<Card> deck = player.getDeckRef().getActiveDeckRef();
            for (int i = 0; i < field.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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
                if (field.size() > 20) {
                    controller.setSmallCard();
                }
                farmNodes.add(card);
            }
            for (int i = 0; i < deck.size(); i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/card.fxml"));
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

    public void onHarvest(int id, ActionEvent event) {
        GameState gs = GameState.getInstance();
        PlaceableCard cs = gs.getCurrentPlayer().getFieldRef().getFieldRef().get(id);
        CardFactory cf = new CardFactory();
        if (!gs.getCurrentPlayer().getDeckRef().isActiveDeckFull()) {
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
        } else {
            MainApplication.getInstance().showInvalidMovePopup(event);
        }
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
        } else if (sourceparts[0].equals("deck")) {
            PlaceableCard targetCard = player.getFieldRef().getFieldRef().get(targetIndex);
            Card sourceCard = player.getDeckRef().getActiveDeckRef().get(sourceIndex);
            if (!currentField.equals(gs.getCurrentPlayer().getName())) {
                PlaceableCard targetCardEnemy = gs.getEnemyPlayer().getFieldRef().getFieldRef().get(targetIndex);
                if (targetCardEnemy != null) {
                    if (sourceCard instanceof Destroy) {
                        if (targetCardEnemy == null) {
                            MainApplication.getInstance().showInvalidMovePopup(event);
                        } else {
                            if (!targetCardEnemy.isProtected()) {
                                gs.getEnemyPlayer().getFieldRef().removeCard(targetIndex);
                            }
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            SwapField();
                        }
                    } else if (sourceCard instanceof Delay) {
                        if (targetCardEnemy == null) {
                            MainApplication.getInstance().showInvalidMovePopup(event);
                        } else {
                            if (!targetCardEnemy.isProtected()) {
                                ((Delay) sourceCard).use(targetCardEnemy);
                                targetCardEnemy.addEffect(sourceCard.getName());
                            }
                            gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                            SwapField();
                        }
                    }
                } else {
                    MainApplication.getInstance().showInvalidMovePopup(event);
                }
            } else {
                if (sourceCard instanceof Layout) {
                    gs.getCurrentPlayer().getFieldRef().expandField();
                    gs.getCurrentPlayer().getDeckRef().removeFromActiveDeck(sourceCard);
                    expandCount.put(gs.getCurrentPlayer().getName(), 5);
                    BackSwapField();
                } else if ((targetCard != null)) {
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
