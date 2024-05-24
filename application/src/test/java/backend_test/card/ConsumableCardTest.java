package backend_test.card;

import app.card.ConsumableCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumableCardTest {

    @Test
    public void testConstructorAndGetters() {
        ConsumableCard card = new ConsumableCard("Test Card", 5, 10);
        assertEquals("Test Card", card.getName());
        assertEquals(5, card.getAddedWeight());
        assertEquals(10, card.getPrice());
    }

    @Test
    public void testCopyConstructor() {
        ConsumableCard originalCard = new ConsumableCard("Original Card", 10, 20);
        ConsumableCard copiedCard = new ConsumableCard(originalCard);

        assertEquals(originalCard.getName(), copiedCard.getName());
        assertEquals(originalCard.getAddedWeight(), copiedCard.getAddedWeight());
        assertEquals(originalCard.getPrice(), copiedCard.getPrice());
    }
}