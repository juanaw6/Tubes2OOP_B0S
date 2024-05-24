package backend_test.card;

import app.card.ConsumableCard;
import app.card.animals.Animal;
import app.card.animals.Carnivore;
import app.card.crops.Crop;
import app.card.effects.Accelerate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccelerateTest {

    @Test
    public void testUseOnAnimal() {
        Animal animal = new Carnivore("Carnivore", new ConsumableCard("Meat", 5, 5), 10);
        animal.setWeight(10);
        Accelerate accelerate = new Accelerate("Accelerate");
        accelerate.use(animal);
        assertEquals(18, animal.getWeight());
    }

    @Test
    public void testUseOnCrop() {
        Crop crop = new Crop("Crop", new ConsumableCard("Fruit", 5, 5), 10);
        crop.setAge(5);
        Accelerate accelerate = new Accelerate("Accelerate");
        accelerate.use(crop);
        assertEquals(7, crop.getAge());
    }
}
