package backend_test.card;

import app.card.ConsumableCard;
import app.card.animals.Animal;
import app.card.animals.Carnivore;
import app.card.animals.Herbivore;
import app.card.animals.Omnivore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    public void testAnimalConstructor() {
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Animal animal = new Carnivore("Test Animal", harvestResult, 100);
        assertEquals("Test Animal", animal.getName());
        assertEquals(100, animal.getHarvestWeight());
        assertEquals(0, animal.getWeight());
    }

    @Test
    public void testSetWeight() {
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Animal animal = new Carnivore("Test Animal", harvestResult, 100);
        animal.setWeight(50);
        assertEquals(50, animal.getWeight());
    }

    @Test
    public void testIsReadyToHarvest() {
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Animal animal = new Carnivore("Test Animal", harvestResult, 100);
        animal.setWeight(100);
        assertTrue(animal.isReadyToHarvest());
    }

    @Test
    public void testCarnivoreConsume() throws Exception {
        ConsumableCard food = new ConsumableCard("Daging Domba", 10, 5);
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Carnivore carnivore = new Carnivore("Test Carnivore", harvestResult, 100);
        carnivore.consume(food);
        assertEquals(10, carnivore.getWeight());

        ConsumableCard wrongFood = new ConsumableCard("Labu", 5, 3);
        Exception exception = assertThrows(Exception.class, () -> carnivore.consume(wrongFood));
        assertEquals("Wrong Food", exception.getMessage());
    }

    @Test
    public void testHerbivoreConsume() throws Exception {
        ConsumableCard food = new ConsumableCard("Jagung", 10, 5);
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Herbivore herbivore = new Herbivore("Test Herbivore", harvestResult, 100);
        herbivore.consume(food);
        assertEquals(10, herbivore.getWeight());

        ConsumableCard wrongFood = new ConsumableCard("Daging Domba", 5, 3);
        Exception exception = assertThrows(Exception.class, () -> herbivore.consume(wrongFood));
        assertEquals("Wrong Food", exception.getMessage());
    }

    @Test
    public void testOmnivoreConsume() throws Exception {
        ConsumableCard food = new ConsumableCard("Jagung", 10, 5);
        ConsumableCard harvestResult = new ConsumableCard("Harvest Result", 10, 5);
        Omnivore omnivore = new Omnivore("Test Omnivore", harvestResult, 100);
        omnivore.consume(food);
        assertEquals(10, omnivore.getWeight());

        ConsumableCard anotherFood = new ConsumableCard("Daging Domba", 15, 7);
        omnivore.consume(anotherFood);
        assertEquals(25, omnivore.getWeight());
    }
}