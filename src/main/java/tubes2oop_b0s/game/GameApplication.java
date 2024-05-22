package tubes2oop_b0s.game;

import api.FileConverter;
import jsonplugin.JsonConverter;
import tubes2oop_b0s.card.*;
import tubes2oop_b0s.card.animals.*;
import tubes2oop_b0s.card.crops.*;
import tubes2oop_b0s.card.effects.*;
import tubes2oop_b0s.loader.LoaderManager;
import tubes2oop_b0s.state.GameState;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.store.Store;

public class GameApplication {
    public static void main(String[] args) {
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        LoaderManager lm = LoaderManager.getInstance();
        lm.loadGameState("C:/Users/LENOVO/OneDrive/Desktop/Tubes2OOP_B0S/src/main/resources/saves", "txt");
        System.out.println("Current player gulden: " + gs.getCurrentPlayer().getGulden());
        System.out.println("Current player gulden: " + gs.getCurrentPlayer().getDeckRef().toString());

        FileConverter cv = new JsonConverter();
        cv.convertToTxt("C:/Users/LENOVO/OneDrive/Desktop/Tubes2OOP_B0S/src/main/resources/test_json");
    }

}
