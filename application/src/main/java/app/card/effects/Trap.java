package app.card.effects;

import app.card.Card;
import app.card.EffectCard;
import app.deck.CardFactory;
import app.deck.ICardFactory;

public class Trap extends Card implements EffectCard {

    public Trap(String name) {
        super(name);
    }

    public Card use() {
        ICardFactory cardFactory = new CardFactory();
        return cardFactory.createCard("Beruang");
    }
}