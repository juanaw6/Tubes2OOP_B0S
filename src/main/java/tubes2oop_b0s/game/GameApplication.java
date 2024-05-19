package tubes2oop_b0s.game;

import tubes2oop_b0s.card.*;
import tubes2oop_b0s.card.animals.*;
import tubes2oop_b0s.card.crops.*;
import tubes2oop_b0s.card.effects.*;

public class GameApplication {
    public static void main(String[] args) {
        Card myCarnivore = new Carnivore("Lion", "A strong carnivore");
        myCarnivore.play();

        Card myCrop = new Crop("Corn", "A nutritious crop");
        myCrop.play();

        Card mySuperCard = new Delay("Time Stop", "Delays all opponents");
        mySuperCard.play();
    }

}
