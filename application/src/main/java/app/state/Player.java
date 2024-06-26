package app.state;

import app.deck.Deck;
import app.field.Field;

public class Player {
    private Deck deck;
    private Field field;
    private int gulden;
    private String name;

    public Player(String name) {
        this.name = name;
        this.deck = new Deck();
        this.field = new Field();
        this.gulden = 0;
    }

    public int getGulden() {
        return gulden;
    }

    public void setGulden(int gulden) {
        this.gulden = gulden;
    }

    public Deck getDeckRef() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Field getFieldRef() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
