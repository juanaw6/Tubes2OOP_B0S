package tubes2oop_b0s.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tubes2oop_b0s.card.Card;

public class Store {
    private static Store instance;
    private ArrayList<Card> products;

    private Store() {
        // Initialize the store data
        products = new ArrayList<>(100);
    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public void displayProducts() {
        // Method to display products in Store
        Map<String, Integer> productCounts = new HashMap<>();
        for (Card card : products) {
            productCounts.put(card.getName(), productCounts.getOrDefault(card.getName(), 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : productCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void handleBuy(Card card, int quantity) {
        int count = 0;
        for (Card product : products) {
            if (product.getName().equals(card.getName())) {
                count++;
            }
        }

        if (quantity<=count) {
            for (int i = 0; i < quantity; i++) {
                products.remove(card);
            }

            System.out.println("Membeli " + quantity + " " + card.getName() + " dari toko");
        } else {
            System.out.println("Jumlah " + card.getName() + " di toko tidak cukup");
        }
    }

    public void handleBuy(Card card) {
        handleBuy(card, 1);
    }

    public void handleSell(Card card, int quantity) {
        for (int i = 0; i < quantity; i++) {
            products.add(card);
        }
        System.out.println("Menjual " + quantity + " " + card.getName());
    }

    public void handleSell(Card card) {
        handleSell(card, 1);
    }
}