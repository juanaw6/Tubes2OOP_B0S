package tubes2oop_b0s.deck;

import org.junit.Before;
import org.junit.Test;
import tubes2oop_b0s.card.Card;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DeckTest {

    private Deck deck;

    @Before
    public void setUp() {
        deck = new Deck();
    }

    @Test
    public void testInitShuffledDeck() {
        assertNotNull(deck.getActiveDeckRef());
        assertNotNull(deck.getRandomShuffled());
        assertEquals(deck.getShuffledDeckSize(), 40);
        assertEquals(deck.getActiveDeckSize(), 0);
    }

    @Test
    public void testAddAndRemoveFromActiveDeck() {
        Card testCard = new Card("Test Card", 0);
        deck.addToActiveDeck(testCard);
        assertEquals(deck.getActiveDeckSize(), 1);
        assertTrue(deck.getActiveDeckRef().contains(testCard));

        deck.removeFromActiveDeck(testCard);
        assertEquals(deck.getActiveDeckSize(), 0);
        assertFalse(deck.getActiveDeckRef().contains(testCard));
    }

    @Test
    public void testAddAndRemoveMultipleFromActiveDeck() {
        Card testCard1 = new Card("Test Card 1", 0);
        Card testCard2 = new Card("Test Card 2", 1);
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(testCard1);
        testCards.add(testCard2);

        deck.addToActiveDeck(testCards);
        assertEquals(deck.getActiveDeckSize(), 2);
        assertTrue(deck.getActiveDeckRef().contains(testCard1));
        assertTrue(deck.getActiveDeckRef().contains(testCard2));

        deck.removeFromActiveDeck(testCards);
        assertEquals(deck.getActiveDeckSize(), 0);
        assertFalse(deck.getActiveDeckRef().contains(testCard1));
        assertFalse(deck.getActiveDeckRef().contains(testCard2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddToActiveDeckWithInvalidIndex() {
        Card testCard = new Card("Test Card", 0);
        deck.addToActiveDeck(10, testCard);
    }
}
