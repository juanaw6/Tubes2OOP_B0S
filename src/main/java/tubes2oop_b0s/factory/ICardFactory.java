package tubes2oop_b0s.factory;

import tubes2oop_b0s.card.Card;

public interface ICardFactory {
    Card createCard(String type);
}