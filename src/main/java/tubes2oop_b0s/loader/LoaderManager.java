package tubes2oop_b0s.loader;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class LoaderManager {
    private static LoaderManager instance = null;
    private final HashMap<String, GameStateLoader> loaders;

    private LoaderManager() {
        loaders = new HashMap<>();
        loaders.put("txt", new TxtLoader());
    }

    public static LoaderManager getInstance() {
        if (instance == null) {
            instance = new LoaderManager();
        }
        return instance;
    }

    public boolean loadLoaderFromJar(String jarFilePath, String className) {
        File jarFile = new File(jarFilePath);
        if (!jarFile.exists() || !jarFile.isFile()) {
            System.err.println("Invalid JAR file path: " + jarFilePath);
            return false;
        }

        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader())) {
            Class<?> cls = urlClassLoader.loadClass(className);
            Object externalLoader = cls.getDeclaredConstructor().newInstance();
            if (externalLoader instanceof GameStateLoader loader) {
                loaders.put(loader.getSupportedFileExtension(), loader);
                System.out.println("Loader added: " + className);
                return true;
            } else {
                GameStateLoader adapter = new ExternalGameStateLoaderAdapter(externalLoader);
                loaders.put(adapter.getSupportedFileExtension(), adapter);
                System.out.println("Adapter for external loader added: " + className);
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error loading class: " + e.getMessage());
        }
        return false;
    }

    public void loadGameState(String folderPath, String extension) {
        GameStateLoader loader = loaders.get(extension);
        if (loader != null) {
            loader.loadGameState(folderPath);
        } else {
            System.err.println("No loader registered for extension: " + extension);
        }
    }

    public void saveGameState(String folderPath, String extension) {
        GameStateLoader loader = loaders.get(extension);
        if (loader != null) {
            loader.saveGameState(folderPath);
        } else {
            System.err.println("No loader registered for extension: " + extension);
        }
    }
}