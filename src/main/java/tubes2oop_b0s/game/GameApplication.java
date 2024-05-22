package tubes2oop_b0s.game;

import api.FileConverter;
import jsonplugin.JsonConverter;
import tubes2oop_b0s.card.*;
import tubes2oop_b0s.card.animals.*;
import tubes2oop_b0s.card.crops.*;
import tubes2oop_b0s.card.effects.*;
import tubes2oop_b0s.loader.LoaderAdapter;
import tubes2oop_b0s.loader.LoaderManager;
import tubes2oop_b0s.state.GameState;
import tubes2oop_b0s.state.Player;
import tubes2oop_b0s.store.Store;
import yamlplugin.YamlConverter;

import java.io.File;

public class GameApplication {
    public static void main(String[] args) {
        GameState gs = GameState.getInstance();
        Store store = Store.getInstance();
        LoaderManager lm = LoaderManager.getInstance();
        FileConverter jsoncv = new JsonConverter();
        FileConverter yamlcv = new YamlConverter();
//        lm.addLoader(new LoaderAdapter(jsoncv));
//        lm.addLoader(new LoaderAdapter(yamlcv));
        lm.loadLoaderFromJar("C:\\Users\\LENOVO\\OneDrive\\Desktop\\Tubes2OOP_B0S\\src\\main\\java\\tubes2oop_b0s\\loader\\YamlConverter.jar");
        lm.loadLoaderFromJar("C:\\Users\\LENOVO\\OneDrive\\Desktop\\Tubes2OOP_B0S\\src\\main\\java\\tubes2oop_b0s\\loader\\JsonConverter.jar");
        String folder = "C:/Users/LENOVO/OneDrive/Desktop/Tubes2OOP_B0S/src/main/resources/test_yaml";

        lm.loadGameState(folder, "yaml");
        System.out.println("Current player gulden: " + gs.getCurrentPlayer().getGulden());
        System.out.println("Current player gulden: " + gs.getCurrentPlayer().getDeckRef().toString());

        lm.saveGameState(folder, "txt");
//        yamlcv.convertToTxt(folder);
//        jsoncv.convertFromTxt(folder);
    }

}
