package tubes2oop_b0s.field;

import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.crops.Crop;

import java.util.ArrayList;

public class Field {
    private final ArrayList<PlaceableCard> field;
    private final int NORMAL_FIELD_CARD_COUNT = 20;

    public Field() {
        field = new ArrayList<>(NORMAL_FIELD_CARD_COUNT);
        for (int i = 0; i < NORMAL_FIELD_CARD_COUNT; i++) {
            field.add(null);
        }
    }

    public ArrayList<PlaceableCard> getFieldRef() {
        return field;
    }

    public ArrayList<PlaceableCard> getFieldCopy() {
        return new ArrayList<>(field);
    }

    public void incrementAllCropAge() {
        for (PlaceableCard card : field) {
            if (card != null) {
                if (card instanceof Crop crop) {
                    crop.incrementAge();
                }
            }
        }
    }

    public void activateAllEffects() {
        for (PlaceableCard card : field) {
            if (card != null) {
                card.activateEffects();
            }
        }
    }

    public void addPlaceableCard(int index, PlaceableCard card) {
        if (index >= 0 && index < field.size()) {
            field.set(index, card);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void removeCard(int index) {
        if (index >= 0 && index < field.size()) {
            field.set(index, null);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public void expandField() {
        int MIN_FIELD_CARD_COUNT = NORMAL_FIELD_CARD_COUNT - 8;
        if (field.size() == NORMAL_FIELD_CARD_COUNT || field.size() == MIN_FIELD_CARD_COUNT) {
            for (int i = 0; i < 10; i++) {
                field.add(null);
            }
        }
    }

    public ArrayList<PlaceableCard> shrinkField() {
        int MAX_FIELD_CARD_COUNT = NORMAL_FIELD_CARD_COUNT + 10;
        ArrayList<PlaceableCard> pushedCards = new ArrayList<>();
        if (field.size() == MAX_FIELD_CARD_COUNT || field.size() > NORMAL_FIELD_CARD_COUNT) {
            for (int i = 0; i < 10; i++) {
                int lastIndex = field.size() - 1;
                PlaceableCard card = field.get(lastIndex);
                if (card != null) {
                    pushedCards.add(card);
                }
                field.remove(lastIndex);
            }
        }
        return pushedCards;
    }

    public int getCardInFieldCount() {
        int count = 0;
        for (int i = 0; i < NORMAL_FIELD_CARD_COUNT; i++) {
            if (field.get(i) != null) {
                count++;
            }
        }
        return count;
    }

    public int getNORMAL_FIELD_CARD_COUNT() {
        return NORMAL_FIELD_CARD_COUNT;
    }
}
