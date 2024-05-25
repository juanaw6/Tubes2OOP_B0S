package app.loader;

import app.card.Card;
import app.card.PlaceableCard;
import app.card.animals.Animal;
import app.card.crops.Crop;
import app.deck.CardFactory;
import app.deck.Deck;
import app.deck.ICardFactory;
import app.field.Field;
import app.state.GameState;
import app.state.Player;
import app.store.Store;
import app.utils.LocationParser;
import app.utils.StringFormatter;

import java.io.*;
import java.util.ArrayList;

public class TxtLoader implements GameStateLoader {

    @Override
    public void loadGameState(String folderPath) {
        try {
            GameState gameState = GameState.getInstance();
            Store store = Store.getInstance();
            BufferedReader gameReader = new BufferedReader(new FileReader(folderPath + "/gamestate.txt"));
            int currentTurn = Integer.parseInt(gameReader.readLine().trim());
            gameState.setTurn(currentTurn);

            int numberOfItems = Integer.parseInt(gameReader.readLine().trim());
            for (int i = 0; i < numberOfItems; i++) {
                String[] itemName = gameReader.readLine().trim().split(" ");
                for (int j = 0; j < Integer.parseInt(itemName[1]); j++) {
                    store.addItem(StringFormatter.formatString(itemName[0]));
                }
            }
            gameReader.close();

            Player player1 = loadPlayer(new BufferedReader(new FileReader(folderPath + "/player1.txt")), "Player 1");
            gameState.setPlayer1(player1);

            Player player2 = loadPlayer(new BufferedReader(new FileReader(folderPath + "/player2.txt")), "Player 2");
            gameState.setPlayer2(player2);

            gameState.setCurrentPlayerIndex((currentTurn + 1) % 2);

        } catch (IOException e) {
            System.err.println("Error loading game state from files: " + e.getMessage());
        }
    }

    @Override
    public void saveGameState(String folderPath) {
        try {
            GameState gameState = GameState.getInstance();
            Store store = Store.getInstance();

            // Save game state
            BufferedWriter gameWriter = new BufferedWriter(new FileWriter(folderPath + "/gamestate.txt"));
            gameWriter.write(String.valueOf(gameState.getTurn()));
            gameWriter.newLine();

            ArrayList<String> storeItems = store.getItemsUniqueStr();
            gameWriter.write(String.valueOf(storeItems.size()));
            gameWriter.newLine();
            for (String item : storeItems) {
                gameWriter.write(StringFormatter.unformatString(item) + " " + store.getQuantity(item));
                gameWriter.newLine();
            }
            gameWriter.close();

            savePlayer(new BufferedWriter(new FileWriter(folderPath + "/player1.txt")), gameState.getPlayer1());

            savePlayer(new BufferedWriter(new FileWriter(folderPath + "/player2.txt")), gameState.getPlayer2());

        } catch (IOException e) {
            System.err.println("Error saving game state to files: " + e.getMessage());
        }
    }

    @Override
    public String getSupportedFileExtension() {
        return "txt";
    }

    private Player loadPlayer(BufferedReader reader, String name) throws IOException {
        ICardFactory cardFactory = new CardFactory();

        Player player = new Player(name);

        int gulden = Integer.parseInt(reader.readLine().trim());
        player.setGulden(gulden);

        int deckSize = Integer.parseInt(reader.readLine().trim());

        Deck newDeck = new Deck(deckSize);
        player.setDeck(newDeck);

        int numActiveCards = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < numActiveCards; i++) {
            String[] cardInfo = reader.readLine().trim().split(" ");
            int location = LocationParser.parseForActiveDeck(cardInfo[0]);
            String cardType = StringFormatter.formatString(cardInfo[1]);
            Card card = cardFactory.createCard(cardType);
            player.getDeckRef().addToActiveDeck(location, card);
        }

        int fieldCardCount = Integer.parseInt(reader.readLine().trim());
        for (int i = 0; i < fieldCardCount; i++) {
            String[] fieldInfo = reader.readLine().trim().split(" ");
            int location = LocationParser.parseForField(fieldInfo[0]);
            String cardType = StringFormatter.formatString(fieldInfo[1]);
            int ageOrWeight = Integer.parseInt(fieldInfo[2]);
            int numItems = Integer.parseInt(fieldInfo[3]);
            ArrayList<String> items = new ArrayList<>();
            for (int j = 0; j < numItems; j++) {
                items.add(StringFormatter.formatString(fieldInfo[4 + j]));
            }

            PlaceableCard placeableCard = (PlaceableCard) cardFactory.createCard(cardType);
            for (String item : items) {
                placeableCard.addEffect(item);
            }

            if (placeableCard instanceof Crop crop) {
                crop.setAge(ageOrWeight);
            } else if (placeableCard instanceof Animal animal) {
                animal.setWeight(ageOrWeight);
            }

            player.getFieldRef().addPlaceableCard(location, placeableCard);
        }

        reader.close();
        return player;
    }

    private void savePlayer(BufferedWriter writer, Player player) throws IOException {
        writer.write(String.valueOf(player.getGulden()));
        writer.newLine();

        Deck deck = player.getDeckRef();
        writer.write(String.valueOf(deck.getShuffledDeckSize()));
        writer.newLine();

        ArrayList<Card> activeDeck = deck.getActiveDeckRef();
        writer.write(String.valueOf(deck.getActiveDeckSize()));
        writer.newLine();
        for (int i = 0; i < activeDeck.size(); i++) {
            Card card = activeDeck.get(i);
            if (card != null) {
                writer.write(
                        LocationParser.convertForActiveDeck(i) + " " + StringFormatter.unformatString(card.getName()));
                writer.newLine();
            }
        }

        Field field = player.getFieldRef();
        ArrayList<PlaceableCard> fieldArray = field.getFieldRef();
        writer.write(String.valueOf(field.getCardInFieldCount()));
        writer.newLine();
        for (int i = 0; i < field.getNORMAL_FIELD_CARD_COUNT(); i++) {
            PlaceableCard placeableCard = fieldArray.get(i);
            if (placeableCard != null) {
                int ageOrWeight = 0;
                if (placeableCard instanceof Animal animal) {
                    ageOrWeight = animal.getWeight();
                } else if (placeableCard instanceof Crop crop) {
                    ageOrWeight = crop.getAge();
                }
                writer.write(LocationParser.convertForField(i) + " "
                        + StringFormatter.unformatString(placeableCard.getName()) + " " +
                        ageOrWeight + " " + placeableCard.getEffectsAsListStr().size());
                for (String effect : placeableCard.getEffectsAsListStr()) {
                    writer.write(" " + StringFormatter.unformatString(effect));
                }
                writer.newLine();
            }
        }

        writer.close();
    }
}