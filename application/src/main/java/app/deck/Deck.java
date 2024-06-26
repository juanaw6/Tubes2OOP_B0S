package app.deck;

import org.jetbrains.annotations.NotNull;
import app.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Deck {
    private final int MAX_ACTIVE_DECK_SIZE = 6;
    private final int MAX_SHUFFLED_DECK_SIZE = 40;
    private final ArrayList<Card> activeDeck = new ArrayList<>();
    private final ArrayList<Card> shuffledDeck = new ArrayList<>();

    public Deck() {
        initShuffledDeck();
        for (int i = 0; i < MAX_ACTIVE_DECK_SIZE; i++) {
            activeDeck.add(null);
        }
    }

    public Deck(int shuffledCardCount) {
        initShuffledDeck(shuffledCardCount);
        for (int i = 0; i < MAX_ACTIVE_DECK_SIZE; i++) {
            activeDeck.add(null);
        }
    }

    public void initShuffledDeck() {
        ICardFactory factory = new CardFactory();
        String[] cardTypes = {
                "Hiu Darat", "Domba", "Sapi", "Kuda", "Ayam",
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Susu", "Daging Domba", "Daging Kuda", "Telur",
                "Jagung", "Stroberi",
                "Accelerate", "Delay", "Instant Harvest", "Destroy", "Protect", "Trap", "Layout"
        };
        String[] mustHaveCards = {
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Accelerate", "Protect", "Instant Harvest", "Trap",
                "Biji Jagung", "Biji Labu", "Biji Stroberi"
        };

        int totalCards = 0;
        java.util.Random rand = new java.util.Random();
        int lengthMustHaveCard = mustHaveCards.length;

        while (totalCards < lengthMustHaveCard) {
            String selectedType = mustHaveCards[rand.nextInt(lengthMustHaveCard)];
            shuffledDeck.add(factory.createCard(selectedType));
            totalCards++;
        }

        while (totalCards < MAX_SHUFFLED_DECK_SIZE) {
            String selectedType = cardTypes[rand.nextInt(cardTypes.length)];
            shuffledDeck.add(factory.createCard(selectedType));
            totalCards++;
        }
        reshuffleDeck();
    }

    public void initShuffledDeck(int n) {
        ICardFactory factory = new CardFactory();
        shuffledDeck.clear();
        java.util.Random rand = new java.util.Random();
        String[] cardTypes = {
                "Hiu Darat", "Domba", "Sapi", "Kuda", "Ayam",
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Susu", "Daging Domba", "Daging Kuda", "Telur",
                "Jagung", "Stroberi",
                "Accelerate", "Delay", "Instant Harvest", "Destroy", "Protect", "Trap", "Layout"
        };
        String[] mustHaveCards = {
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Accelerate", "Protect", "Instant Harvest", "Trap"
        };
        // int remainingCards = MAX_SHUFFLED_DECK_SIZE - turn * 4;
        int remainingCards = n;
        remainingCards = Math.max(0, remainingCards);
        int lengthMustHaveCard = mustHaveCards.length;
        int totalCards = 0;
        if (remainingCards > MAX_SHUFFLED_DECK_SIZE - lengthMustHaveCard) {
            while (totalCards < lengthMustHaveCard) {
                String selectedType = mustHaveCards[rand.nextInt(lengthMustHaveCard)];
                shuffledDeck.add(factory.createCard(selectedType));
                totalCards++;
            }
        }

        for (int i = totalCards; i < remainingCards; i++) {
            String selectedType = cardTypes[rand.nextInt(cardTypes.length)];
            shuffledDeck.add(factory.createCard(selectedType));
        }

        reshuffleDeck();
    }

    public ArrayList<Card> getRandomShuffled() {
        ArrayList<Card> drawnCards = new ArrayList<>();
        int neededCards = MAX_ACTIVE_DECK_SIZE - (int) activeDeck.stream().filter(Objects::nonNull).count();
        neededCards = Math.min(4, neededCards);
        neededCards = Math.min(shuffledDeck.size(), neededCards);
        for (int i = 0; i < neededCards; i++) {
            Card card = shuffledDeck.get(i);
            drawnCards.add(card);
        }
        reshuffleDeck();
        return drawnCards;
    }

    public void reshuffleDeck() {
        Collections.shuffle(shuffledDeck);
    }

    public void removeFromShuffled(Card card) {
        this.shuffledDeck.remove(card);
    }

    public void removeFromShuffled(@NotNull ArrayList<Card> cards) {
        for (Card card : cards) {
            this.removeFromShuffled(card);
        }
    }

    public void removeFromActiveDeck(Card card) {
        for (int i = 0; i < activeDeck.size(); i++) {
            if (activeDeck.get(i) == null) {
                continue;
            }
            if (activeDeck.get(i).equals(card)) {
                activeDeck.set(i, null);
                break;
            }
        }
    }

    public void removeFromActiveDeck(int index) {
        if (index >= 0 && index < activeDeck.size()) {
            activeDeck.set(index, null);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void addToActiveDeck(Card card) {
        for (int i = 0; i < activeDeck.size(); i++) {
            if (activeDeck.get(i) == null) {
                this.activeDeck.set(i, card);
                break;
            }
        }
    }

    public void addToActiveDeck(int index, Card card) {
        if (index >= 0 && index < activeDeck.size()) {
            this.activeDeck.set(index, card);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void addToActiveDeck(@NotNull ArrayList<Card> cards) {
        for (Card card : cards) {
            this.addToActiveDeck(card);
        }
    }

    public ArrayList<Card> getActiveDeckRef() {
        return activeDeck;
    }

    public ArrayList<Card> getActiveDeckCopy() {
        return new ArrayList<>(activeDeck);
    }

    public int getShuffledDeckSize() {
        return shuffledDeck.size();
    }

    public int getActiveDeckSize() {
        int count = 0;
        for (Card card : activeDeck) {
            if (card != null) {
                count++;
            }
        }
        return count;
    }

    public boolean isActiveDeckFull() {
        for (Card card : activeDeck) {
            if (card == null) {
                return false;
            }
        }
        return true;
    }

}