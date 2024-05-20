package tubes2oop_b0s.card.animals;

import tubes2oop_b0s.card.ConsumableCard;

public class Omnivore extends Animal {

    public Omnivore(String name, ConsumableCard harvestResult, int harvestWeight) {
        super(name, harvestResult, harvestWeight);
    }

    @Override
    public void consume(ConsumableCard consumableCard) {
        this.setWeight(this.getWeight() + consumableCard.getAddedWeight());
    }
}