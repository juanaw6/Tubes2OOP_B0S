package tubes2oop_b0s.card;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlacableCard extends Card {
    private HashMap<String, Integer> currentEffect;

    public PlacableCard(String name) {
        super(name);
    }

    public abstract void update();

    public boolean isProtected() {

    }
}