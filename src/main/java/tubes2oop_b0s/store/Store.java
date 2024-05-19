package tubes2oop_b0s.store;

import java.util.ArrayList;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.resizable_list.ResizableCardList;

public class Store {
    private static Store instance;
    private ResizableCardList<Card> products;

    private Store() {
        // Initialize the store data
        products = new ResizableCardList<Card>(100);
    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void displayProducts() {
        // Method to display products in Store
    }
}