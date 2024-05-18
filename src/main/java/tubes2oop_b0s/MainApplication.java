package tubes2oop_b0s;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false); // Make the window non-resizable
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}