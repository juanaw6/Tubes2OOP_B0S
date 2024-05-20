package tubes2oop_b0s.store;

import java.util.ArrayList;

import tubes2oop_b0s.card.Card;

public class Store {
    private static Store instance;
    private ArrayList<Card> products;

    private Store() {
        // Initialize the store data
        products = new ArrayList<Card>(100);
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