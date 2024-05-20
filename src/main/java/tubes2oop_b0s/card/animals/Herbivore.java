package tubes2oop_b0s.card.animals;

import tubes2oop_b0s.card.ConsumableCard;

import java.util.ArrayList;
import java.util.Arrays;

public class Herbivore extends Animal {

    public Herbivore(String name, ConsumableCard harvestResult, int harvestWeight) {
        super(name, harvestResult, harvestWeight);
    }

    @Override
    public void consume(ConsumableCard consumableCard) throws Exception {
        ArrayList<String> allowedFoods = new ArrayList<>(Arrays.asList("Jagung", "Labu", "Stroberi"));
        if (allowedFoods.contains(consumableCard.getName())) {
            this.setWeight(this.getWeight() + consumableCard.getAddedWeight());
        } else {
            throw new Exception("Wrong Food");
        }
    }
}
