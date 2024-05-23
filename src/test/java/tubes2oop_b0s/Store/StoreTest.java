package tubes2oop_b0s.store;

import org.junit.Before;
import org.junit.Test;
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.state.Player;

import static org.junit.Assert.*;

public class StoreTest {

    private Store store;
    private Player player;

    @Before
    public void setUp() {
        store = Store.getInstance();
        player = new Player("Test Player");
    }

    @Test
    public void testAddAndRemoveItem() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);
        assertTrue(store.getItemsRef().contains(testCard));

        store.removeItem(testCard);
        assertFalse(store.getItemsRef().contains(testCard));
    }

    @Test
    public void testBuyAndSellItem() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);

        store.buyItem(testCard, player);
        assertTrue(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertFalse(store.getItemsRef().contains(testCard));
        assertEquals(player.getGulden(), 0);

        store.sellItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(player.getGulden(), 10);
    }

    @Test
    public void testGetQuantity() {
        ConsumableCard testCard1 = new ConsumableCard("Test Card 1", 0, 10);
        ConsumableCard testCard2 = new ConsumableCard("Test Card 2", 0, 20);

        store.addItem(testCard1);
        store.addItem(testCard2);

        assertEquals(store.getQuantity("Test Card 1"), 1);
        assertEquals(store.getQuantity("Test Card 2"), 1);
        assertEquals(store.getQuantity("Non-existent Card"), 0);
    }

    @Test
    public void testClearStore() {
        ConsumableCard testCard1 = new ConsumableCard("Test Card 1", 0, 10);
        ConsumableCard testCard2 = new ConsumableCard("Test Card 2", 0, 20);

        store.addItem(testCard1);
        store.addItem(testCard2);

        assertFalse(store.getItemsRef().isEmpty());

        store.clear();

        assertTrue(store.getItemsRef().isEmpty());
    }
}
