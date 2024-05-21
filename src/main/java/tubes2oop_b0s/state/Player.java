package tubes2oop_b0s.state;

import tubes2oop_b0s.factory.Deck;
import tubes2oop_b0s.field.Field;

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

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Field getField() {
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
