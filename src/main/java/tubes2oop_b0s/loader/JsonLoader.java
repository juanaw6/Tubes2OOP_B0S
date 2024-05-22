//package tubes2oop_b0s.loader;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import tubes2oop_b0s.card.PlaceableCard;
//import tubes2oop_b0s.card.animals.Animal;
//import tubes2oop_b0s.card.crops.Crop;
//import tubes2oop_b0s.deck.CardFactory;
//import tubes2oop_b0s.deck.Deck;
//import tubes2oop_b0s.deck.ICardFactory;
//import tubes2oop_b0s.state.GameState;
//import tubes2oop_b0s.state.Player;
//import tubes2oop_b0s.store.Store;
//import tubes2oop_b0s.utils.LocationParser;
//import tubes2oop_b0s.utils.StringFormatter;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//public class JsonLoader implements GameStateLoader {
//    private static class StoreItem {
//        public String name;
//        public int quantity;
//    }
//
//    private static class ActiveDeckCard {
//        public String location;
//        public String card_type;
//    }
//
//    private static class FieldCard {
//        public String location;
//        public String card_type;
//        public Integer age_or_weight;
//        public List<String> effects;
//    }
//
//    private final ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public void loadGameState(String folderPath) {
//        try {
//            GameState gamestate = GameState.getInstance();
//            File file = new File(folderPath + "/gamestate.json");
//            JsonNode rootNode = mapper.readTree(file);
//
//            int turn = rootNode.get("turn").asInt();
//            gamestate.setTurn(turn);
//
//            List<StoreItem> items = mapper.readValue(rootNode.get("store").toString(), new TypeReference<>() {});
//            Store store = Store.getInstance();
//            store.clear();
//            for (StoreItem item : items) {
//                for (int i = 0; i < item.quantity; i++) {
//                    store.addItem(item.name);
//                }
//            }
//
//            Player player1 = loadPlayer(folderPath + "/player1.json", "Player 1");
//            gamestate.setPlayer1(player1);
//
//            Player player2 = loadPlayer(folderPath + "/player2.json", "Player 2");
//            gamestate.setPlayer2(player2);
//
//        } catch (IOException e) {
//            System.err.println("Error loading game state from JSON: " + e.getMessage());
//        }
//        return folderPath;
//    }
//
//    @Override
//    public void saveGameState(String folderPath) {
//
//    }
//
//    @Override
//    public String getSupportedFileExtension() {
//        return "json";
//    }
//
//    private Player loadPlayer(String filePath, String name) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        File file = new File(filePath);
//        JsonNode rootNode = mapper.readTree(file);
//
//        Player player = new Player(name);
//        int gulden = rootNode.get("gulden").asInt();
//        player.setGulden(gulden);
//
//        int deck_count = rootNode.get("deck").asInt();
//        Deck deck = new Deck(deck_count);
//        player.setDeck(deck);
//
//        ICardFactory cardFactory = new CardFactory();
//
//        List<ActiveDeckCard> activeDeckCards = mapper.readValue(rootNode.get("active_deck").toString(), new TypeReference<List<ActiveDeckCard>>() {});
//        for (ActiveDeckCard card : activeDeckCards) {
//            int location = LocationParser.parseForActiveDeck(card.location);
//            player.getDeckRef().addToActiveDeck(location, cardFactory.createCard(StringFormatter.formatString(card.card_type)));
//        }
//
//        List<FieldCard> fieldCards = mapper.readValue(rootNode.get("field").toString(), new TypeReference<List<FieldCard>>() {});
//        for (FieldCard card : fieldCards) {
//            int location = LocationParser.parseForField(card.location);
//            PlaceableCard placeableCard = (PlaceableCard) cardFactory.createCard(StringFormatter.formatString(card.card_type));
//            for (String item : card.effects) {
//                placeableCard.addEffect(StringFormatter.formatString(item));
//            }
//
//            int ageOrWeight = card.age_or_weight;
//            if (placeableCard instanceof Crop crop) {
//                crop.setAge(ageOrWeight);
//            } else if (placeableCard instanceof Animal animal) {
//                animal.setWeight(ageOrWeight);
//            }
//
//            player.getFieldRef().addPlaceableCard(location, placeableCard);
//        }
//        return player;
//    }
//}