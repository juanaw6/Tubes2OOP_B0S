package tubes2oop_b0s.loader;

public interface GameStateLoader {
    void loadGameState(String folderPath);
    void saveGameState(String folderPath);
    String getSupportedFileExtension();
}
