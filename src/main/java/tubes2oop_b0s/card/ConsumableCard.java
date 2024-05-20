package tubes2oop_b0s.card;

public class ConsumableCard extends Card {
    private int addedWeight;

    public ConsumableCard(String name, int addedWeight) {
        super(name);
        this.addedWeight = addedWeight;
    }

    public int getAddedWeight() {
        return addedWeight;
    }

    public void setAddedWeight(int addedWeight) {
        this.addedWeight = addedWeight;
    }
}