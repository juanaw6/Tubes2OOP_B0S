package app.deck;

import app.card.Card;

public interface ICardFactory {
    Card createCard(String type);
}