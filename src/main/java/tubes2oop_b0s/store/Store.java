package tubes2oop_b0s.store;

import java.util.HashMap;
import java.util.Map;

import tubes2oop_b0s.card.Card;

public class Store {
    private static Store instance;
    private Map<Card, Integer> products;

    private Store() {
        // Initialize the store data
        products = new HashMap<>();
    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void displayProducts() {
        // Method to display products in Store
        for (Map.Entry<Card, Integer> entry : products.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
    }

    public void handleBuy(Card card, int quantity) {
        if (products.containsKey(card) && products.get(card) >= quantity) {
            products.put(card, products.get(card) - quantity);
            System.out.println("Membeli " + quantity + " " + card.getName()+" dari toko");
        } else {
            System.out.println("Jumlah " + card.getName() + " di toko tidak cukup");
        }
    }

    public void handleSell(Card card, int quantity) {
        products.put(card, products.getOrDefault(card, 0) + quantity);
        System.out.println("Menjual " + quantity + " " + card.getName());
    }
}