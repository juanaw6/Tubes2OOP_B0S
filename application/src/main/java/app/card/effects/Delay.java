package app.card.effects;

import app.card.Card;
import app.card.EffectCard;
import app.card.PlaceableCard;
import app.card.animals.Animal;
import app.card.crops.Crop;

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