package app.store;

import app.card.ConsumableCard;
import app.deck.CardFactory;
import app.deck.ICardFactory;
import app.state.Player;
import app.utils.StringFormatter;

import java.util.ArrayList;

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
        if (player.getGulden() < card.getPrice() || !items.contains(card)) {
            return;
        }
        player.getDeckRef().addToActiveDeck(card);
        player.setGulden(player.getGulden() - card.getPrice());
        removeItem(card);
    }

    public void sellItem(ConsumableCard card, Player player) {
        if (!player.getDeckRef().getActiveDeckRef().contains(card)) {
            return;
        }
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

    public ArrayList<String> getItemsUniqueStr() {
        ArrayList<String> temp = new ArrayList<>();
        for (ConsumableCard item : items) {
            if (!temp.contains(item.getName())) {
                temp.add(item.getName());
            }
        }
        return temp;
    }

    public int getQuantity(String name) {
        int count = 0;
        for (ConsumableCard item : items) {
            if (item.getName().equals(name)) {
                count++;
            }
        }
        return count;
    }
}