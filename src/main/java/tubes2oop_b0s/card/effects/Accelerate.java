package tubes2oop_b0s.card.effects;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.crops.Crop;

public class Accelerate extends Card implements EffectCard {

    public Accelerate(String name) {
        super(name);
    }

    public void use(PlaceableCard card) {
        if (card instanceof Animal animal) {
            animal.setWeight(animal.getWeight() + 8);
        } else if (card instanceof Crop crop) {
            crop.setAge(crop.getAge() + 2);
        }
    }
}