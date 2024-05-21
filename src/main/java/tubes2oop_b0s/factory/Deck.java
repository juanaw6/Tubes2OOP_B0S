package tubes2oop_b0s.factory;

import org.jetbrains.annotations.NotNull;
import tubes2oop_b0s.card.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Deck {
    private final int MAX_ACTIVE_DECK_SIZE = 6;
    private final int MAX_SHUFFLED_DECK_SIZE = 40;
    private final ArrayList<Card> activeDeck = new ArrayList<Card>(MAX_ACTIVE_DECK_SIZE);
    private final ArrayList<Card> shuffledDeck = new ArrayList<Card>(MAX_SHUFFLED_DECK_SIZE);

    public Deck() {
        initShuffledDeck();
        for (int i = 0; i < MAX_ACTIVE_DECK_SIZE; i++) {
            activeDeck.add(null);
        }
    }

    // INI MAU GIMANA KALAU PAS LOAD UBAH LAGI YE
    public Deck(int turn) {
        initShuffledDeck(turn);
        for (int i = 0; i < MAX_ACTIVE_DECK_SIZE; i++) {
            activeDeck.add(null);
        }
    }

    public void initShuffledDeck() {
        CardFactory factory = new CardFactory();
        String[] cardTypes = {
                "Hiu Darat", "Domba", "Sapi", "Kuda", "Ayam", "Beruang",
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Sirip Hiu", "Susu", "Daging Domba", "Daging Kuda", "Telur", "Daging Beruang",
                "Jagung", "Labu", "Stroberi",
                "Accelerate", "Delay", "Instant harvest", "Destroy", "Protect", "Trap"
        };
        String[] mustHaveCard = {
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Accelerate", "Protect", "Instant harvest", "Trap",
                "Biji Jagung", "Biji Labu", "Biji Stroberi"
        };

        int totalCards = 0;
        java.util.Random rand = new java.util.Random();
        int lengthMustHaveCard = mustHaveCard.length;

        while (totalCards < lengthMustHaveCard) {
            String selectedType = mustHaveCard[rand.nextInt(lengthMustHaveCard)];
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

    public void initShuffledDeck(int turn) {
        CardFactory factory = new CardFactory();
        shuffledDeck.clear();
        java.util.Random rand = new java.util.Random();
        String[] cardTypes = {
                "Hiu Darat", "Domba", "Sapi", "Kuda", "Ayam", "Beruang",
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Sirip Hiu", "Susu", "Daging Domba", "Daging Kuda", "Telur", "Daging Beruang",
                "Jagung", "Labu", "Stroberi",
                "Accelerate", "Delay", "Instant harvest", "Destroy", "Protect", "Trap"
        };
        String[] mustHaveCard = {
                "Biji Jagung", "Biji Labu", "Biji Stroberi",
                "Accelerate", "Protect", "Instant harvest", "Trap"
        };
        int remainingCards = MAX_SHUFFLED_DECK_SIZE - turn * 4;
        remainingCards = Math.max(0, remainingCards);
        int lengthMustHaveCard = mustHaveCard.length;
        int totalCards = 0;
        if (remainingCards > MAX_SHUFFLED_DECK_SIZE - lengthMustHaveCard) {
            while (totalCards < lengthMustHaveCard) {
                String selectedType = mustHaveCard[rand.nextInt(lengthMustHaveCard)];
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
        for (int i = 0; i < neededCards; i++) {
            if (!shuffledDeck.isEmpty()) {
                Card card = shuffledDeck.remove(0);
                drawnCards.add(card);
            }
        }
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
            if (activeDeck.get(i).equals(card)) {
                activeDeck.set(i, null);
                break;
            }
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

    public void addToActiveDeck(@NotNull ArrayList<Card> cards) {
        for (Card card : cards) {
            this.addToActiveDeck(card);
        }
    }

}
