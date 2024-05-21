package tubes2oop_b0s.factory;

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
        switch (type) {
            case "Hiu Darat":
                return new Carnivore(type, new ConsumableCard("Sirip Hiu", 12, 500), 20);
            case "Domba":
                return new Herbivore(type, new ConsumableCard("Daging Domba", 6, 120), 12);
            case "Sapi":
                return new Herbivore(type, new ConsumableCard("Susu", 4, 100), 10);
            case "Kuda":
                return new Herbivore(type, new ConsumableCard("Daging Kuda", 8, 150), 14);
            case "Ayam":
                return new Omnivore(type, new ConsumableCard("Telur", 2, 50), 5);
            case "Beruang":
                return new Omnivore(type, new ConsumableCard("Daging Beruang", 12, 500), 25);
            case "Biji Jagung":
                return new Crop(type, new ConsumableCard("Jagung", 3, 150), 3);
            case "Biji Labu":
                return new Crop(type, new ConsumableCard("Labu", 10, 500), 5);
            case "Biji Stroberi":
                return new Crop(type, new ConsumableCard("Stroberi", 5, 350), 4);
            case "Sirip Hiu":
                return new ConsumableCard(type, 12, 500);
            case "Susu":
                return new ConsumableCard(type, 4, 100);
            case "Daging Domba":
                return new ConsumableCard(type, 6, 120);
            case "Daging Kuda":
                return new ConsumableCard(type, 8, 150);
            case "Telur":
                return new ConsumableCard(type, 2, 50);
            case "Daging Beruang":
                return new ConsumableCard(type, 12, 500);
            case "Jagung":
                return new ConsumableCard(type, 3, 150);
            case "Labu":
                return new ConsumableCard(type, 10, 500);
            case "Stroberi":
                return new ConsumableCard(type, 5, 350);
            case "Accelerate":
                return new Accelerate(type);
            case "Delay":
                return new Delay(type);
            case "Instant harvest":
                return new InstantHarvest(type);
            case "Destroy":
                return new Destroy(type);
            case "Protect":
                return new Protect(type);
            case "Trap":
                return new Trap(type);
            default:
                throw new IllegalArgumentException("Unknown card type: " + type);
        }

    }
}
