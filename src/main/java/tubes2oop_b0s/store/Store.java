package tubes2oop_b0s.store;

import java.util.ArrayList;

import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.deck.CardFactory;
import tubes2oop_b0s.deck.ICardFactory;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.utils.StringFormatter;

public class Store {
    private static Store instance = null;
    private final ArrayList<ConsumableCard> items;

    private Store() {
        items = new ArrayList<>();
    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void clear() {
        items.clear();
    }

    public void addItem(String name) {
        ICardFactory cardFactory = new CardFactory();
        items.add((ConsumableCard) cardFactory.createCard(name));
    }

    public void addItem(ConsumableCard card) {
        items.add(card);
    }

    public void removeItem(String name) {
        for (ConsumableCard item : items) {
            if (item.getName().equals(name)) {
                items.remove(item);
                break;
            }
        }
    }

    public void removeItem(ConsumableCard card) {
        items.remove(card);
    }

    public void buyItem(ConsumableCard card, Player player) {
        player.getDeckRef().addToActiveDeck(card);
        player.setGulden(player.getGulden() - card.getPrice());
        removeItem(card);
    }

    public void sellItem(ConsumableCard card, Player player) {
        player.getDeckRef().removeFromActiveDeck(card);
        player.setGulden(player.getGulden() + card.getPrice());
        addItem(card);
    }

    public ArrayList<ConsumableCard> getItemsRef() {
        return items;
    }

    public ArrayList<ConsumableCard> getItemsCopy() {
        return new ArrayList<>(items);
    }

    public int getQuantity(String name) {
        int count = 0;
        for (ConsumableCard item : items) {
            if (item.getName().equals(StringFormatter.formatString(name))) {
                count++;
            }
        }
        return count;
    }
}