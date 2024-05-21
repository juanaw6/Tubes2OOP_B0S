package tubes2oop_b0s.field;

import tubes2oop_b0s.card.PlaceableCard;
import tubes2oop_b0s.card.crops.Crop;

import java.util.ArrayList;

public class Field {
    private ArrayList<PlaceableCard> field;
    private final int NORMAL_FIELD_CARD_COUNT = 20;

    public Field() {
        field = new ArrayList<>(NORMAL_FIELD_CARD_COUNT);
    }

    public ArrayList<PlaceableCard> getField() {
        return field;
    }

    public void incrementAllCropAge() {
        for (PlaceableCard card : field) {
            if (card instanceof Crop crop) {
                crop.incrementAge();
            }
        }
    }

    public void addPlaceableCard(int index, PlaceableCard card) {
        field.set(index, card);
    }

    public void setNullIndex(int index) {
        field.set(index, null);
    }

    public void expandField() {
        if (field.size() == NORMAL_FIELD_CARD_COUNT) {
            for (int i = 0; i < 10; i++) {
                field.add(null);
            }
        }
    }

    public void shrinkField() {
        int MAX_FIELD_CARD_COUNT = NORMAL_FIELD_CARD_COUNT + 10;
        if (field.size() == MAX_FIELD_CARD_COUNT) {
            for (int i = 0; i < 10; i++) {
                field.remove(field.size() - 1);
            }
        }
    }
}
