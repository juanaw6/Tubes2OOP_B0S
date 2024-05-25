package app.loader;

public interface GameStateLoader {
    boolean loadGameState(String folderPath);
    void saveGameState(String folderPath);
    String getSupportedFileExtension();
}
