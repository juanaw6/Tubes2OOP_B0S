package app.card.animals;

import app.card.ConsumableCard;

public class Omnivore extends Animal {

    public Omnivore(String name, ConsumableCard harvestResult, int harvestWeight) {
        super(name, harvestResult, harvestWeight);
    }

    @Override
    public void consume(ConsumableCard consumableCard) {
        this.setWeight(this.getWeight() + consumableCard.getAddedWeight());
    }
}