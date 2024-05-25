package backend_test.state;

import app.deck.Deck;
import app.field.Field;
import app.state.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private final String playerName = "Test Player";

    @BeforeEach
    public void setUp() {
        player = new Player(playerName);
    }

    @Test
    public void testPlayerConstructor() {
        assertNotNull(player.getDeckRef());
        assertNotNull(player.getFieldRef());
        assertEquals(playerName, player.getName());
        assertEquals(0, player.getGulden());
    }

    @Test
    public void testGetAndSetGulden() {
        player.setGulden(100);
        assertEquals(100, player.getGulden());
        player.setGulden(200);
        assertEquals(200, player.getGulden());
    }

    @Test
    public void testGetAndSetDeck() {
        Deck newDeck = new Deck();
        player.setDeck(newDeck);
        assertEquals(newDeck, player.getDeckRef());
    }

    @Test
    public void testGetAndSetField() {
        Field newField = new Field();
        player.setField(newField);
        assertEquals(newField, player.getFieldRef());
    }

    @Test
    public void testGetAndSetName() {
        player.setName("New Player Name");
        assertEquals("New Player Name", player.getName());
    }

    @Test
    public void testInitialGuldenValue() {
        assertEquals(0, player.getGulden());
    }

    @Test
    public void testSetNegativeGulden() {
        player.setGulden(-100);
        assertEquals(-100, player.getGulden());
    }

    @Test
    public void testDeckReference() {
        Deck deck1 = player.getDeckRef();
        Deck deck2 = player.getDeckRef();
        assertSame(deck1, deck2);
    }

    @Test
    public void testFieldReference() {
        Field field1 = player.getFieldRef();
        Field field2 = player.getFieldRef();
        assertSame(field1, field2);
    }

    @Test
    public void testPlayerEquality() {
        Player anotherPlayer = new Player(playerName);
        assertEquals(player.getName(), anotherPlayer.getName());
        assertNotEquals(player, anotherPlayer);
    }

    @Test
    public void testSetGuldenMultipleTimes() {
        player.setGulden(100);
        player.setGulden(200);
        player.setGulden(300);
        assertEquals(300, player.getGulden());
    }

    @Test
    public void testChangeDeckReference() {
        Deck originalDeck = player.getDeckRef();
        Deck newDeck = new Deck();
        player.setDeck(newDeck);
        assertNotSame(originalDeck, player.getDeckRef());
    }

    @Test
    public void testChangeFieldReference() {
        Field originalField = player.getFieldRef();
        Field newField = new Field();
        player.setField(newField);
        assertNotSame(originalField, player.getFieldRef());
    }

    @Test
    public void testSetNameToNull() {
        player.setName(null);
        assertNull(player.getName());
    }

    @Test
    public void testSetNameToEmptyString() {
        player.setName("");
        assertEquals("", player.getName());
    }

    @Test
    public void testSetGuldenToMaxValue() {
        player.setGulden(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, player.getGulden());
    }

    @Test
    public void testSetGuldenToMinValue() {
        player.setGulden(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, player.getGulden());
    }

    @Test
    public void testSetDeckToNull() {
        player.setDeck(null);
        assertNull(player.getDeckRef());
    }

    @Test
    public void testSetFieldToNull() {
        player.setField(null);
        assertNull(player.getFieldRef());
    }
}