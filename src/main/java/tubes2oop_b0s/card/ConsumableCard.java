package tubes2oop_b0s.card;

public class ConsumableCard extends Card {
    private final int addedWeight;
    private final int price;

    public ConsumableCard(String name, int addedWeight, int price) {
        super(name);
        this.addedWeight = addedWeight;
        this.price = price;
    }

    public ConsumableCard(ConsumableCard card) {
        super(card.getName());
        this.addedWeight = card.getAddedWeight();
        this.price = card.price;
    }

    public int getAddedWeight() {
        return addedWeight;
    }

    public int getPrice() {
        return price;
    }
}