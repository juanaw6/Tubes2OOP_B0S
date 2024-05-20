package tubes2oop_b0s.card.effects;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.crops.Crop;

public class Delay extends Card implements EffectCard {

    public Delay(String name) {
        super(name);
    }

    public void use(PlaceableCard card) {
        if (card instanceof Animal animal) {
            animal.setWeight(animal.getWeight() - 5);
            if (animal.getWeight() <= 0) {
                animal.setWeight(0);
            }
        } else if (card instanceof Crop crop) {
            crop.setAge(crop.getAge() - 2);
            if (crop.getAge() <= 0) {
                crop.setAge(0);
            }
        }
    }
}