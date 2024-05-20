package tubes2oop_b0s.card.crops;

import tubes2oop_b0s.card.PlacableCard;

public class Crop extends PlacableCard {
    private int age;

    public Crop(String name, String description) {
        super(name, description);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
