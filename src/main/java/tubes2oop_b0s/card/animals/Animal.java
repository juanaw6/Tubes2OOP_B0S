package tubes2oop_b0s.card.animals;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlacableCard;

public abstract class Animal extends PlacableCard {
    private int weight;
    private EffectCard currentEffect;

    public Animal(String name, EffectCard currentEffect) {
        super(name);
        this.currentEffect = currentEffect;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public abstract void consume(ConsumableCard consumableCard);
    // Additional animal-specific methods can be added here
}
