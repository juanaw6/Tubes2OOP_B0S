package tubes2oop_b0s.card;

public abstract class Card {
    private String name;
    private String description;

    public Card(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void play(); // Abstract method to be implemented by all specific cards
}
