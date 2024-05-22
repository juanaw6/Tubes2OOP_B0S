package tubes2oop_b0s.card;

import tubes2oop_b0s.card.effects.Accelerate;
import tubes2oop_b0s.card.effects.Delay;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class PlaceableCard extends Card {
    private HashMap<String, Integer> effects;
    private final ConsumableCard harvestResult;

    public PlaceableCard(String name, ConsumableCard harvestResult) {
        super(name);
        this.harvestResult = harvestResult;
        this.effects = new HashMap<>();
    }

    public void activateEffects() {
        int delayCount = effects.getOrDefault("Delay", 0);
        int accelerateCount = effects.getOrDefault("Accelerate", 0);

        Delay delayEffect = new Delay("Delay");
        Accelerate accelerateEffect = new Accelerate("Accelerate");

        for (int i = 0; i < delayCount; i++) {
            delayEffect.use(this);
        }
        for (int i = 0; i < accelerateCount; i++) {
            accelerateEffect.use(this);
        }
    }

    public abstract boolean isReadyToHarvest();

    public boolean isProtected() {
        return this.effects.containsKey("Protect");
    }

    public boolean hasTrap() {
        return this.effects.containsKey("Trap");
    }

    public ConsumableCard harvest() {
        return new ConsumableCard(harvestResult);
    }

    public HashMap<String, Integer> getEffects() {
        return new HashMap<>(effects);
    }

    public void setEffects(HashMap<String, Integer> effects) {
        this.effects = new HashMap<>(effects);
    }

    public void addEffect(String effect) {
        effects.put(effect, effects.getOrDefault(effect, 0) + 1);
    }

    public void setOrAddEffect(String effect, int amount) {
        this.effects.put(effect, amount);
    }

    public void setOrAddEffect(Card effectCard, int amount) {
        this.effects.put(effectCard.getName(), amount);
    }

    public void removeEffect(String effect) {
        this.effects.remove(effect);
    }

    public ArrayList<String> getEffectsAsListStr() {
        ArrayList<String> effectsList = new ArrayList<>();
        for (String effect : effects.keySet()) {
            int count = effects.get(effect);
            for (int i = 0; i < count; i++) {
                effectsList.add(effect);
            }
        }
        return effectsList;
    }
}