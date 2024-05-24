package app.card.crops;

import app.card.ConsumableCard;
import app.card.PlaceableCard;

public class Crop extends PlaceableCard {
    private int age;
    private final int harvestAge;

    public Crop(String name, ConsumableCard harvestResult, int harvestAge) {
        super(name, harvestResult);
        this.harvestAge = harvestAge;
        this.age = 0;
    }

    @Override
    public boolean isReadyToHarvest() {
        return this.age >= harvestAge;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void incrementAge() {
        this.age++;
    }

    public int getHarvestAge() {
        return harvestAge;
    }
}
