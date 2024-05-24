package app.loader;

public interface GameStateLoader {
    void loadGameState(String folderPath);
    void saveGameState(String folderPath);
    String getSupportedFileExtension();
}
