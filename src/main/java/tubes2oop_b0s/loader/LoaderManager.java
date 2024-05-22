package tubes2oop_b0s.loader;

import api.FileConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

    public boolean loadLoaderFromJar(String jarFilePath) {
        File jarFile = new File(jarFilePath);
        if (!jarFile.exists() || !jarFile.isFile()) {
            System.err.println("Invalid JAR file path: " + jarFilePath);
            return false;
        }

        try (URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader());
             JarFile jar = new JarFile(jarFile)) {

            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    String classNameFromEntry = entryName.replace('/', '.').replace(".class", "");
                    try {
                        // Use reflection to load the class dynamically
                        Class<?> cls = urlClassLoader.loadClass(classNameFromEntry);
                        // Use reflection to check if the class implements FileConverter
                        if (FileConverter.class.isAssignableFrom(cls) && !cls.isInterface() && !cls.isAnonymousClass()) {
                            // Use reflection to create an instance of the class
                            FileConverter converter = (FileConverter) cls.getDeclaredConstructor().newInstance();
                            addLoader(new LoaderAdapter(converter));
                            System.out.println("Loader added support: " + converter.supportedExtension());
                            return true;
                        }
                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        // Log and ignore, continue checking other classes
                        System.err.println("Class not found or could not be loaded: " + classNameFromEntry);
                    } catch (ReflectiveOperationException e) {
                        // Log and continue checking other classes
                        System.err.println("Error instantiating class: " + classNameFromEntry + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error accessing JAR file: " + e.getMessage());
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

    public ArrayList<String> getSupportedFileExtensions() {
        return new ArrayList<>(loaders.keySet());
    }

    public void addLoader(GameStateLoader gameStateLoader) {
        loaders.put(gameStateLoader.getSupportedFileExtension(), gameStateLoader);
    }
}