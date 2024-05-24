package app.card.effects;

import app.card.Card;
import app.card.EffectCard;
import app.card.PlaceableCard;
import app.card.animals.Animal;
import app.card.crops.Crop;

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