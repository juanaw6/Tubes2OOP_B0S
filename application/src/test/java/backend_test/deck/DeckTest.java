package backend_test.deck;

import app.deck.Deck;
import app.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testInitShuffledDeck() {
        assertNotNull(deck.getActiveDeckRef());
        assertNotNull(deck.getRandomShuffled());
        assertEquals(40, deck.getShuffledDeckSize());
        assertEquals(0, deck.getActiveDeckSize());
    }

    @Test
    public void testAddAndRemoveFromActiveDeck() {
        Card testCard = new Card("Test Card");
        deck.addToActiveDeck(testCard);
        assertEquals(1, deck.getActiveDeckSize());
        assertTrue(deck.getActiveDeckRef().contains(testCard));

        deck.removeFromActiveDeck(testCard);
        assertEquals(0, deck.getActiveDeckSize());
        assertFalse(deck.getActiveDeckRef().contains(testCard));
    }

    @Test
    public void testAddAndRemoveMultipleFromActiveDeck() {
        Card testCard1 = new Card("Test Card 1");
        Card testCard2 = new Card("Test Card 2");
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(testCard1);
        testCards.add(testCard2);

        deck.addToActiveDeck(testCards);
        assertEquals(2, deck.getActiveDeckSize());
        assertTrue(deck.getActiveDeckRef().contains(testCard1));
        assertTrue(deck.getActiveDeckRef().contains(testCard2));
    }

    @Test
    public void testAddToActiveDeckWithInvalidIndex() {
        Card testCard = new Card("Test Card");
        assertThrows(IndexOutOfBoundsException.class, () -> deck.addToActiveDeck(10, testCard));
    }
}