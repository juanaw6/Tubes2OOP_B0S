package tubes2oop_b0s.factory;

import tubes2oop_b0s.card.Card;

public interface CardFactory {
    Card createCard(String type);
    // type nya masih blm ditentuin
}