package tubes2oop_b0s.resizable_list;

import java.util.ArrayList;
import tubes2oop_b0s.card.*;

public class ResizableCardList<T extends Card> {
    private ArrayList<T> cards;
    // list supaya bisa lebih mudah klo ambil bonus resize (gunakan perhitungan
    // untuk bisa akses)

    public ResizableCardList(int initialCapacity) {
        cards = new ArrayList<>(initialCapacity);
    }

    public void ensureCapacity(int minCapacity) {
        cards.ensureCapacity(minCapacity);
        while (cards.size() < minCapacity) {
            cards.add(null);
        }
    }

    public void setCardAt(T card, int index) {
        if (index >= 0 && index < cards.size()) {
            cards.set(index, card);
        } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + cards.size());
        }
    }

    public T getCardAt(int index) {
        return cards.get(index);
    }

    public int getSize() {
        return cards.size();
    }
}