package tubes2oop_b0s.card;

import java.util.HashMap;

public abstract class PlacableCard extends Card {
    private HashMap<String, Integer> currentEffect;

    public PlacableCard(String name) {
        super(name);
    }

    public abstract void update();

    public boolean isProtected() {
        return true;
    }

    public HashMap<String, Integer> getCurrentEffect() {
        return currentEffect;
    }

    public void setCurrentEffect(HashMap<String, Integer> currentEffect) {
        this.currentEffect = currentEffect;
    }
}