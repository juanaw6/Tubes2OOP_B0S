package tubes2oop_b0s.card.effects;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.EffectCard;
import tubes2oop_b0s.deck.CardFactory;
import tubes2oop_b0s.deck.ICardFactory;

public class Trap extends Card implements EffectCard {

    public Trap(String name) {
        super(name);
    }

    public Card use() {
        ICardFactory cardFactory = new CardFactory();
        return cardFactory.createCard("Beruang");
    }
}