package app.card.animals;

import app.card.ConsumableCard;
import app.card.PlaceableCard;

public abstract class Animal extends PlaceableCard {
    private int weight;
    private final int harvestWeight;

    public Animal(String name, ConsumableCard harvestResult, int harvestWeight) {
        super(name, harvestResult);
        this.harvestWeight = harvestWeight;
        this.weight = 0;
    }

    @Override
    public boolean isReadyToHarvest() {
        return this.weight >= this.harvestWeight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public abstract void consume(ConsumableCard consumableCard) throws Exception;

    public int getHarvestWeight() {
        return harvestWeight;
    }
}
