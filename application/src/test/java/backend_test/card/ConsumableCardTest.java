package backend_test.card;

import app.card.ConsumableCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumableCardTest {

    @Test
    public void testConsumableCardConstructor() {
        ConsumableCard card = new ConsumableCard("Test Consumable", 10, 5);
        assertEquals("Test Consumable", card.getName());
        assertEquals(10, card.getAddedWeight());
        assertEquals(5, card.getPrice());
    }

    @Test
    public void testConsumableCardCopyConstructor() {
        ConsumableCard original = new ConsumableCard("Original", 10, 5);
        ConsumableCard copy = new ConsumableCard(original);
        assertEquals("Original", copy.getName());
        assertEquals(10, copy.getAddedWeight());
        assertEquals(5, copy.getPrice());
    }
}



