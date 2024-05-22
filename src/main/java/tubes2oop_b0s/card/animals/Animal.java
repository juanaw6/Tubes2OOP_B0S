package tubes2oop_b0s.card.animals;

import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.effects.Accelerate;
import tubes2oop_b0s.card.effects.Delay;

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
