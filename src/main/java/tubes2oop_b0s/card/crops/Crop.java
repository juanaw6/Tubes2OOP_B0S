package tubes2oop_b0s.card.crops;

import tubes2oop_b0s.card.PlacableCard;

public class Crop extends PlacableCard {
    private int age;

    public Crop(String name, int age) {
        super(name);
        this.age = age;
    }

    @Override
    public void update() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
