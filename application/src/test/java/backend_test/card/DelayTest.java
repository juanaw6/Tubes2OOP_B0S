package backend_test.card;

import app.card.ConsumableCard;
import app.card.animals.Animal;
import app.card.animals.Carnivore;
import app.card.crops.Crop;
import app.card.effects.Delay;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DelayTest {

    @Test
    public void testUseOnAnimal() {
        Animal animal = new Carnivore("Carnivore", new ConsumableCard("Meat", 5, 5), 10);
        animal.setWeight(10);
        Delay delay = new Delay("Delay");
        delay.use(animal);
        assertEquals(5, animal.getWeight());
    }

    @Test
    public void testUseOnCrop() {
        Crop crop = new Crop("Crop", new ConsumableCard("Fruit", 5, 5), 10);
        crop.setAge(5);
        Delay delay = new Delay("Delay");
        delay.use(crop);
        assertEquals(3, crop.getAge());
    }
}
