package backend_test.card;

import app.card.ConsumableCard;
import app.card.PlaceableCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceableCardTest {

    @Test
    public void testPlaceableCardConstructor() {
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        PlaceableCard card = new PlaceableCard("Test Placeable", harvestResult) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        assertEquals("Test Placeable", card.getName());
        assertEquals(harvestResult.getName(), card.harvest().getName());
    }

    @Test
    public void testAddEffect() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Protect");
        assertTrue(card.isProtected());
    }

    @Test
    public void testActivateEffects() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Delay");
        card.addEffect("Accelerate");

        HashMap<String, Integer> effects = card.getEffects();
        assertEquals(1, effects.get("Delay"));
        assertEquals(1, effects.get("Accelerate"));
    }

    @Test
    public void testRemoveEffect() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Protect");
        assertTrue(card.isProtected());
        card.removeEffect("Protect");
        assertFalse(card.isProtected());
    }

    @Test
    public void testSetEffects() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        HashMap<String, Integer> newEffects = new HashMap<>();
        newEffects.put("Protect", 1);
        newEffects.put("Accelerate", 2);
        card.setEffects(newEffects);
        assertEquals(newEffects, card.getEffects());
    }

    @Test
    public void testSetOrAddEffect() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.setOrAddEffect("Protect", 2);
        card.setOrAddEffect("Protect", 3);
        assertEquals(3, card.getEffects().get("Protect"));
    }

    @Test
    public void testGetEffectsAsListStr() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Protect");
        card.addEffect("Accelerate");
        ArrayList<String> effectsList = card.getEffectsAsListStr();
        assertTrue(effectsList.contains("Protect"));
        assertTrue(effectsList.contains("Accelerate"));
    }

    @Test
    public void testIsProtected() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Protect");
        assertTrue(card.isProtected());
    }

    @Test
    public void testHasTrap() {
        PlaceableCard card = new PlaceableCard("Test Placeable", new ConsumableCard("Harvest Result", 10, 5)) {
            @Override
            public boolean isReadyToHarvest() {
                return true;
            }
        };
        card.addEffect("Trap");
        assertTrue(card.hasTrap());
    }
}