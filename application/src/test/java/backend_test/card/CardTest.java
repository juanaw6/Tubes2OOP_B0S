package backend_test.card;

import app.card.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testCardConstructor() {
        Card card = new Card("Test Card");
        assertEquals("Test Card", card.getName());
    }

    @Test
    public void testCardSetName() {
        Card card = new Card("Test Card");
        card.setName("New Name");
        assertEquals("New Name", card.getName());
    }
}
