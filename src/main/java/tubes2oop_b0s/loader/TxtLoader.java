package tubes2oop_b0s.loader;

import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.crops.Crop;
import tubes2oop_b0s.state.GameState;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.store.Store;
import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.deck.CardFactory;
import tubes2oop_b0s.deck.Deck;
import tubes2oop_b0s.deck.ICardFactory;
import tubes2oop_b0s.utils.LocationParser;
import tubes2oop_b0s.utils.StringFormatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
                String itemName = gameReader.readLine().trim();
                store.addItem(StringFormatter.formatString(itemName));
            }
            gameReader.close();

            Player player1 = loadPlayer(new BufferedReader(new FileReader(folderPath + "/player1.txt")), "Player 1");
            gameState.setPlayer1(player1);

            Player player2 = loadPlayer(new BufferedReader(new FileReader(folderPath + "/player2.txt")), "Player 2");
            gameState.setPlayer2(player2);

        } catch (IOException e) {
            System.err.println("Error loading game state from files: " + e.getMessage());
        }
    }

    @Override
    public void saveGameState(String folderPath) {
        // Implement save logic
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
}