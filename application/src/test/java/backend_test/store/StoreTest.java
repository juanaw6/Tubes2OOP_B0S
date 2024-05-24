package backend_test.store;

import app.card.ConsumableCard;
import app.state.Player;
import app.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private Store store;
    private Player player;

    @BeforeEach
    public void setUp() {
        store = Store.getInstance();
        store.clear();
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
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(0, player.getGulden());

        int initial = 500;

        player.setGulden(initial);
        store.buyItem(testCard, player);
        assertTrue(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertFalse(store.getItemsRef().contains(testCard));
        assertEquals(initial - testCard.getPrice(), player.getGulden());

        store.sellItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(initial, player.getGulden());
    }

    @Test
    public void testGetQuantity() {
        ConsumableCard testCard1 = new ConsumableCard("Test Card 1", 0, 10);
        ConsumableCard testCard2 = new ConsumableCard("Test Card 2", 0, 20);

        store.addItem(testCard1);
        store.addItem(testCard2);

        assertEquals(1, store.getQuantity("Test Card 1"));
        assertEquals(1, store.getQuantity("Test Card 2"));
        assertEquals(0, store.getQuantity("Non-existent Card"));
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