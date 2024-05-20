package tubes2oop_b0s.card.effects;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.animals.Carnivore;

public class Trap extends Card implements EffectCard {

    public Trap(String name) {
        super(name);
    }

    public Card use() {
        // TODO: Use CardFactory
        return new Carnivore("Beruang");
    }
}