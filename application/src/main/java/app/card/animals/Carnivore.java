package app.card.animals;

import app.card.ConsumableCard;

import java.util.ArrayList;
import java.util.Arrays;

public class Carnivore extends Animal {

    public Carnivore(String name, ConsumableCard harvestResult, int harvestWeight) {
        super(name, harvestResult, harvestWeight);
    }

    @Override
    public void consume(ConsumableCard consumableCard) throws Exception {
        ArrayList<String> allowedFoods = new ArrayList<>(Arrays.asList("Sirip Hiu", "Susu", "Telur", "Daging Kuda", "Daging Domba", "Daging Beruang"));
        if (allowedFoods.contains(consumableCard.getName())) {
            this.setWeight(this.getWeight() + consumableCard.getAddedWeight());
        } else {
            throw new Exception("Wrong Food");
        }
    }
}
