package backend_test.deck;

import app.card.Card;
import app.card.ConsumableCard;
import app.deck.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        deck.addToActiveDeck(testCard);
        assertEquals(1, deck.getActiveDeckSize());
        assertTrue(deck.getActiveDeckRef().contains(testCard));

        deck.removeFromActiveDeck(testCard);
        assertEquals(0, deck.getActiveDeckSize());
        assertFalse(deck.getActiveDeckRef().contains(testCard));
    }

    @Test
    public void testAddAndRemoveMultipleFromActiveDeck() {
        Card testCard1 = new ConsumableCard("Test Card 1", 1, 1);
        Card testCard2 = new ConsumableCard("Test Card 2", 1, 1);
        ArrayList<Card> testCards = new ArrayList<>();
        testCards.add(testCard1);
        testCards.add(testCard2);

        deck.addToActiveDeck(testCards);
        assertEquals(2, deck.getActiveDeckSize());
        assertTrue(deck.getActiveDeckRef().contains(testCard1));
        assertTrue(deck.getActiveDeckRef().contains(testCard2));

        for (Card card: testCards) {
            deck.removeFromActiveDeck(card);
        }
        assertEquals(0, deck.getActiveDeckSize());
        assertFalse(deck.getActiveDeckRef().contains(testCard1));
        assertFalse(deck.getActiveDeckRef().contains(testCard2));
    }

    @Test
    public void testAddToActiveDeckWithInvalidIndex() {
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> deck.addToActiveDeck(10, testCard));
    }

    @Test
    public void testRemoveFromActiveDeckWithInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> deck.removeFromActiveDeck(10));
    }

    @Test
    public void testInitShuffledDeckWithCount() {
        deck = new Deck(20);
        assertEquals(20, deck.getShuffledDeckSize());
    }

    @Test
    public void testGetRandomShuffled() {
        deck.initShuffledDeck();
        ArrayList<Card> drawnCards = deck.getRandomShuffled();
        assertNotNull(drawnCards);
        assertTrue(drawnCards.size() <= 6);
    }

    @Test
    public void testReshuffleDeck() {
        deck.initShuffledDeck();
        ArrayList<Card> originalOrder = new ArrayList<>(deck.getRandomShuffled());
        deck.reshuffleDeck();
        ArrayList<Card> newOrder = new ArrayList<>(deck.getRandomShuffled());
        assertNotEquals(originalOrder, newOrder);
    }

    @Test
    public void testGetActiveDeckRef() {
        assertNotNull(deck.getActiveDeckRef());
        assertEquals(6, deck.getActiveDeckRef().size());
    }

    @Test
    public void testGetActiveDeckCopy() {
        assertNotNull(deck.getActiveDeckCopy());
        assertEquals(6, deck.getActiveDeckCopy().size());
    }

    @Test
    public void testGetShuffledDeckSize() {
        assertEquals(40, deck.getShuffledDeckSize());
    }

    @Test
    public void testGetActiveDeckSize() {
        assertEquals(0, deck.getActiveDeckSize());
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        deck.addToActiveDeck(testCard);
        assertEquals(1, deck.getActiveDeckSize());
    }

    @Test
    public void testAddToActiveDeckFull() {
        for (int i = 0; i < 6; i++) {
            deck.addToActiveDeck(new ConsumableCard("Test Card " + i, 1, 1));
        }
        Card extraCard = new ConsumableCard("Extra Card", 1, 1);
        deck.addToActiveDeck(extraCard);
        assertFalse(deck.getActiveDeckRef().contains(extraCard));
    }

    @Test
    public void testRemoveFromActiveDeckByIndex() {
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        deck.addToActiveDeck(testCard);
        deck.removeFromActiveDeck(0);
        assertEquals(0, deck.getActiveDeckSize());
        assertFalse(deck.getActiveDeckRef().contains(testCard));
    }

    @Test
    public void testUniqueShuffledDeck() {
        Set<Card> cardSet = new HashSet<>(deck.getRandomShuffled());
        assertEquals(deck.getRandomShuffled().size(), cardSet.size());
    }

    @Test
    public void testActiveDeckInitialSize() {
        assertEquals(6, deck.getActiveDeckRef().size());
    }

    @Test
    public void testShuffledDeckContainsCards() {
        assertFalse(deck.getRandomShuffled().isEmpty());
    }

    @Test
    public void testActiveDeckContainsNull() {
        for (Card card : deck.getActiveDeckRef()) {
            assertNull(card);
        }
    }

    @Test
    public void testAddToActiveDeckAtSpecificIndex() {
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        deck.addToActiveDeck(0, testCard);
        assertEquals(testCard, deck.getActiveDeckRef().get(0));
    }

    @Test
    public void testAddToActiveDeckAtInvalidIndex() {
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        assertThrows(IndexOutOfBoundsException.class, () -> deck.addToActiveDeck(-1, testCard));
    }

    @Test
    public void testRemoveFromEmptyActiveDeck() {
        deck.removeFromActiveDeck(0);
        assertEquals(6, deck.getActiveDeckRef().size());
    }

    @Test
    public void testAddAndRemoveShuffledDeck() {
        Card testCard = new ConsumableCard("Test Card", 1, 1);
        deck.initShuffledDeck();
        deck.removeFromShuffled(testCard);
        assertFalse(deck.getRandomShuffled().contains(testCard));
    }

    @Test
    public void testAddMultipleToActiveDeck() {
        ArrayList<Card> testCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            testCards.add(new ConsumableCard("Test Card " + i, 1, 1));
        }
        deck.addToActiveDeck(testCards);
        assertEquals(6, deck.getActiveDeckSize());
    }
}