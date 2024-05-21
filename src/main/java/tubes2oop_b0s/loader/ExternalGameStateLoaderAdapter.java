package tubes2oop_b0s.loader;

public class ExternalGameStateLoaderAdapter implements GameStateLoader {
    private final Object externalLoader;

    public ExternalGameStateLoaderAdapter(Object externalLoader) {
        this.externalLoader = externalLoader;
    }

    @Override
    public void loadGameState(String folderPath) {
        try {
            externalLoader.getClass().getMethod("loadGameState", String.class).invoke(externalLoader, folderPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke loadGameState method on external loader", e);
        }
    }

    @Override
    public void saveGameState(String folderPath) {
        try {
            externalLoader.getClass().getMethod("saveGameState", String.class).invoke(externalLoader, folderPath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke saveGameState method on external loader", e);
        }
    }

    @Override
    public String getSupportedFileExtension() {
        try {
            return (String) externalLoader.getClass().getMethod("getSupportedFileExtension").invoke(externalLoader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke getSupportedFileExtension method on external loader", e);
        }
    }
}