package backend_test.store;

import app.card.ConsumableCard;
import app.state.Player;
import app.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    public void testAddAndRemoveItemByName() {
        store.addItem("Telur");
        assertEquals(1, store.getQuantity("Telur"));

        store.removeItem("Telur");
        assertEquals(0, store.getQuantity("Telur"));
    }

    @Test
    public void testBuyAndSellItem() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);

        store.buyItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(0, player.getGulden());

        int initialGulden = 500;
        player.setGulden(initialGulden);
        store.buyItem(testCard, player);
        assertTrue(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertFalse(store.getItemsRef().contains(testCard));
        assertEquals(initialGulden - testCard.getPrice(), player.getGulden());

        store.sellItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(initialGulden, player.getGulden());
    }

    @Test
    public void testBuyItemInsufficientGulden() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 100);
        store.addItem(testCard);
        player.setGulden(50);

        store.buyItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertTrue(store.getItemsRef().contains(testCard));
        assertEquals(50, player.getGulden());
    }

    @Test
    public void testBuyItemNotInStore() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        player.setGulden(50);

        store.buyItem(testCard, player);
        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
        assertEquals(50, player.getGulden());
    }

    @Test
    public void testSellItemNotInDeck() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        player.setGulden(50);

        store.sellItem(testCard, player);
        assertFalse(store.getItemsRef().contains(testCard));
        assertEquals(50, player.getGulden());
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

    @Test
    public void testGetItemsRef() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);

        ArrayList<ConsumableCard> itemsRef = store.getItemsRef();
        assertEquals(1, itemsRef.size());
        assertTrue(itemsRef.contains(testCard));
    }

    @Test
    public void testGetItemsCopy() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);

        ArrayList<ConsumableCard> itemsCopy = store.getItemsCopy();
        assertEquals(1, itemsCopy.size());
        assertTrue(itemsCopy.contains(testCard));
    }

    @Test
    public void testGetItemsUniqueStr() {
        store.addItem("Telur");
        store.addItem("Susu");
        store.addItem("Telur");

        ArrayList<String> uniqueItems = store.getItemsUniqueStr();
        assertEquals(2, uniqueItems.size());
        assertTrue(uniqueItems.contains("Telur"));
        assertTrue(uniqueItems.contains("Susu"));
    }

    @Test
    public void testSellItemAddsToStore() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        player.getDeckRef().addToActiveDeck(testCard);
        store.sellItem(testCard, player);

        assertTrue(store.getItemsRef().contains(testCard));
    }

    @Test
    public void testSellItemRemovesFromPlayerDeck() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        player.getDeckRef().addToActiveDeck(testCard);
        store.sellItem(testCard, player);

        assertFalse(player.getDeckRef().getActiveDeckRef().contains(testCard));
    }

    @Test
    public void testSellItemIncreasesPlayerGulden() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        player.getDeckRef().addToActiveDeck(testCard);
        player.setGulden(0);

        store.sellItem(testCard, player);
        assertEquals(10, player.getGulden());
    }

    @Test
    public void testBuyItemDecreasesPlayerGulden() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);
        player.setGulden(20);

        store.buyItem(testCard, player);
        assertEquals(10, player.getGulden());
    }

    @Test
    public void testBuyItemRemovesFromStore() {
        ConsumableCard testCard = new ConsumableCard("Test Card", 0, 10);
        store.addItem(testCard);
        player.setGulden(20);

        store.buyItem(testCard, player);
        assertFalse(store.getItemsRef().contains(testCard));
    }

    @Test
    public void testAddDuplicateItems() {
        ConsumableCard testCard1 = new ConsumableCard("Test Card", 0, 10);
        ConsumableCard testCard2 = new ConsumableCard("Test Card", 0, 10);

        store.addItem(testCard1);
        store.addItem(testCard2);

        assertEquals(2, store.getQuantity("Test Card"));
    }

    @Test
    public void testRemoveItemByNameWithMultiple() {
        store.addItem("Telur");
        store.addItem("Telur");

        store.removeItem("Telur");
        assertEquals(1, store.getQuantity("Telur"));

        store.removeItem("Telur");
        assertEquals(0, store.getQuantity("Telur"));
    }

    @Test
    public void testRemoveNonExistentItem() {
        store.removeItem("Non-existent Card");
        assertEquals(0, store.getQuantity("Non-existent Card"));
    }

    @Test
    public void testGetItemsUniqueStrWithEmptyStore() {
        ArrayList<String> uniqueItems = store.getItemsUniqueStr();
        assertTrue(uniqueItems.isEmpty());
    }
}