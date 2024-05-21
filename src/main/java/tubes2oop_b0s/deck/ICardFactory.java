package tubes2oop_b0s.deck;

import tubes2oop_b0s.card.Card;

public interface ICardFactory {
    Card createCard(String type);
}