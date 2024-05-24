package backend_test.field;

import app.card.ConsumableCard;
import app.card.PlaceableCard;
import app.card.crops.Crop;
import app.field.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    public void setUp() {
        field = new Field();
    }

    @Test
    public void testFieldConstructor() {
        assertNotNull(field.getFieldRef());
        assertEquals(field.getNORMAL_FIELD_CARD_COUNT(), field.getFieldRef().size());
        assertTrue(field.getFieldRef().stream().allMatch(Objects::isNull));
    }

    @Test
    public void testGetFieldCopy() {
        ArrayList<PlaceableCard> fieldCopy = field.getFieldCopy();
        assertEquals(field.getFieldRef().size(), fieldCopy.size());
        assertNotSame(field.getFieldRef(), fieldCopy);
    }

    @Test
    public void testIncrementAllCropAge() {
        Crop crop1 = new Crop("Crop 1", new ConsumableCard("Harvest Result 1", 10, 5), 3);
        Crop crop2 = new Crop("Crop 2", new ConsumableCard("Harvest Result 2", 10, 5), 3);
        field.addPlaceableCard(0, crop1);
        field.addPlaceableCard(1, crop2);
        field.incrementAllCropAge();
        assertEquals(1, crop1.getAge());
        assertEquals(1, crop2.getAge());
    }

    @Test
    public void testActivateAllEffects() {
        Crop crop1 = new Crop("Crop 1", new ConsumableCard("Harvest Result 1", 10, 5), 3);
        Crop crop2 = new Crop("Crop 2", new ConsumableCard("Harvest Result 2", 10, 5), 3);
        crop1.addEffect("Accelerate");
        crop2.addEffect("Delay");
        field.addPlaceableCard(0, crop1);
        field.addPlaceableCard(1, crop2);
        field.activateAllEffects();
        assertEquals(2, crop1.getAge());
        assertEquals(0, crop2.getAge());
    }

    @Test
    public void testAddPlaceableCard() {
        PlaceableCard crop = new Crop("Crop", new ConsumableCard("Harvest Result", 10, 5), 3);
        field.addPlaceableCard(0, crop);
        assertEquals(crop, field.getFieldRef().get(0));
    }

    @Test
    public void testAddPlaceableCardInvalidIndex() {
        PlaceableCard crop = new Crop("Crop", new ConsumableCard("Harvest Result", 10, 5), 3);
        assertThrows(IndexOutOfBoundsException.class, () -> field.addPlaceableCard(-1, crop));
        assertThrows(IndexOutOfBoundsException.class, () -> field.addPlaceableCard(100, crop));
    }

    @Test
    public void testRemoveCard() {
        PlaceableCard crop = new Crop("Crop", new ConsumableCard("Harvest Result", 10, 5), 3);
        field.addPlaceableCard(0, crop);
        field.removeCard(0);
        assertNull(field.getFieldRef().get(0));
    }

    @Test
    public void testRemoveCardInvalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> field.removeCard(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> field.removeCard(100));
    }

    @Test
    public void testExpandField() {
        field.expandField();
        assertEquals(30, field.getFieldRef().size());
    }

    @Test
    public void testShrinkField() {
        field.expandField();
        PlaceableCard crop1 = new Crop("Crop 1", new ConsumableCard("Harvest Result 1", 10, 5), 3);
        PlaceableCard crop2 = new Crop("Crop 2", new ConsumableCard("Harvest Result 2", 10, 5), 3);
        field.addPlaceableCard(20, crop1);
        field.addPlaceableCard(21, crop2);
        ArrayList<PlaceableCard> pushedCards = field.shrinkField();
        assertEquals(2, pushedCards.size());
        assertEquals(crop1.getName(), pushedCards.get(1).getName());
        assertEquals(crop2.getName(), pushedCards.get(0).getName());
        assertEquals(20, field.getFieldRef().size());
    }

    @Test
    public void testGetCardInFieldCount() {
        assertEquals(0, field.getCardInFieldCount());
        PlaceableCard crop1 = new Crop("Crop 1", new ConsumableCard("Harvest Result 1", 10, 5), 3);
        PlaceableCard crop2 = new Crop("Crop 2", new ConsumableCard("Harvest Result 2", 10, 5), 3);
        field.addPlaceableCard(0, crop1);
        field.addPlaceableCard(1, crop2);
        assertEquals(2, field.getCardInFieldCount());
    }

    @Test
    public void testAddAndRemoveMultipleCards() {
        PlaceableCard crop1 = new Crop("Crop 1", new ConsumableCard("Harvest Result 1", 10, 5), 3);
        PlaceableCard crop2 = new Crop("Crop 2", new ConsumableCard("Harvest Result 2", 10, 5), 3);
        PlaceableCard crop3 = new Crop("Crop 3", new ConsumableCard("Harvest Result 3", 10, 5), 3);
        field.addPlaceableCard(0, crop1);
        field.addPlaceableCard(1, crop2);
        field.addPlaceableCard(2, crop3);
        field.removeCard(1);
        assertEquals(crop1, field.getFieldRef().get(0));
        assertNull(field.getFieldRef().get(1));
        assertEquals(crop3, field.getFieldRef().get(2));
    }

    @Test
    public void testExpandAndShrinkField() {
        field.expandField();
        assertEquals(30, field.getFieldRef().size());
        ArrayList<PlaceableCard> pushedCards = field.shrinkField();
        assertTrue(pushedCards.isEmpty());
        assertEquals(20, field.getFieldRef().size());
    }

    @Test
    public void testIncrementAllCropAgeWithNoCrops() {
        field.incrementAllCropAge();
        assertTrue(field.getFieldRef().stream().allMatch(Objects::isNull));
    }

    @Test
    public void testActivateAllEffectsWithNoEffects() {
        field.activateAllEffects();
        assertTrue(field.getFieldRef().stream().allMatch(Objects::isNull));
    }

    @Test
    public void testAddPlaceableCardToLastIndex() {
        PlaceableCard crop = new Crop("Crop", new ConsumableCard("Harvest Result", 10, 5), 3);
        field.addPlaceableCard(19, crop);
        assertEquals(crop, field.getFieldRef().get(19));
    }

    @Test
    public void testRemoveCardFromLastIndex() {
        PlaceableCard crop = new Crop("Crop", new ConsumableCard("Harvest Result", 10, 5), 3);
        field.addPlaceableCard(19, crop);
        field.removeCard(19);
        assertNull(field.getFieldRef().get(19));
    }

    @Test
    public void testExpandFieldMultipleTimes() {
        field.expandField();
        assertEquals(30, field.getFieldRef().size());
        field.expandField();
        assertEquals(30, field.getFieldRef().size());
    }

    @Test
    public void testShrinkFieldMultipleTimes() {
        field.expandField();
        field.expandField();
        field.shrinkField();
        assertEquals(20, field.getFieldRef().size());
        field.shrinkField();
        assertEquals(12, field.getFieldRef().size());
    }

    @Test
    public void testFieldInitialSize() {
        assertEquals(20, field.getFieldRef().size());
    }

    @Test
    public void testAddPlaceableCardToFullField() {
        for (int i = 0; i < 20; i++) {
            field.addPlaceableCard(i, new Crop("Crop " + i, new ConsumableCard("Harvest Result " + i, 10, 5), 3));
        }
        PlaceableCard extraCard = new Crop("Extra Crop", new ConsumableCard("Extra Harvest Result", 10, 5), 3);
        field.addPlaceableCard(19, extraCard);
        assertTrue(field.getFieldRef().contains(extraCard));
    }
}