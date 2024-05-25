package app.loader;

import api.FileConverter;

public class LoaderAdapter implements GameStateLoader {
    private final FileConverter fileConverter;
    private final TxtLoader txtLoader;

    public LoaderAdapter(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
        this.txtLoader = new TxtLoader();
    }

    @Override
    public boolean loadGameState(String folderPath) {
        // Clean first
        fileConverter.cleanTxtFiles(folderPath);

        // Convert to txt and load
        fileConverter.convertToTxt(folderPath);
        boolean success = txtLoader.loadGameState(folderPath);

        // Clean temp txt
        fileConverter.cleanTxtFiles(folderPath);

        return success;
    }

    @Override
    public void saveGameState(String folderPath) {
        // Clean first
        fileConverter.cleanTxtFiles(folderPath);
        fileConverter.cleanSupportedExtensionFiles(folderPath);

        // Save to txt and convert
        txtLoader.saveGameState(folderPath);
        fileConverter.convertFromTxt(folderPath);

        // Clean temp txt
        fileConverter.cleanTxtFiles(folderPath);
    }

    @Override
    public String getSupportedFileExtension() {
        return fileConverter.supportedExtension();
    }
}
