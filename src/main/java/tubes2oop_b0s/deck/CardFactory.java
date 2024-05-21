package tubes2oop_b0s.deck;

import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.animals.Carnivore;
import tubes2oop_b0s.card.animals.Herbivore;
import tubes2oop_b0s.card.animals.Omnivore;
import tubes2oop_b0s.card.crops.Crop;
import tubes2oop_b0s.card.effects.*;

public class CardFactory implements ICardFactory {
    @Override
    public Card createCard(String type) {
        return switch (type) {
            case "Hiu Darat" -> new Carnivore(type, new ConsumableCard("Sirip Hiu", 12, 500), 20);
            case "Domba" -> new Herbivore(type, new ConsumableCard("Daging Domba", 6, 120), 12);
            case "Sapi" -> new Herbivore(type, new ConsumableCard("Susu", 4, 100), 10);
            case "Kuda" -> new Herbivore(type, new ConsumableCard("Daging Kuda", 8, 150), 14);
            case "Ayam" -> new Omnivore(type, new ConsumableCard("Telur", 2, 50), 5);
            case "Beruang" -> new Omnivore(type, new ConsumableCard("Daging Beruang", 12, 500), 25);
            case "Biji Jagung" -> new Crop(type, new ConsumableCard("Jagung", 3, 150), 3);
            case "Biji Labu" -> new Crop(type, new ConsumableCard("Labu", 10, 500), 5);
            case "Biji Stroberi" -> new Crop(type, new ConsumableCard("Stroberi", 5, 350), 4);
            case "Sirip Hiu" -> new ConsumableCard(type, 12, 500);
            case "Susu" -> new ConsumableCard(type, 4, 100);
            case "Daging Domba" -> new ConsumableCard(type, 6, 120);
            case "Daging Kuda" -> new ConsumableCard(type, 8, 150);
            case "Telur" -> new ConsumableCard(type, 2, 50);
            case "Daging Beruang" -> new ConsumableCard(type, 12, 500);
            case "Jagung" -> new ConsumableCard(type, 3, 150);
            case "Labu" -> new ConsumableCard(type, 10, 500);
            case "Stroberi" -> new ConsumableCard(type, 5, 350);
            case "Accelerate" -> new Accelerate(type);
            case "Delay" -> new Delay(type);
            case "Instant harvest" -> new InstantHarvest(type);
            case "Destroy" -> new Destroy(type);
            case "Protect" -> new Protect(type);
            case "Trap" -> new Trap(type);
            default -> throw new IllegalArgumentException("Unknown card type: " + type);
        };
    }
}
