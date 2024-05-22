package tubes2oop_b0s.loader;

import api.FileConverter;

public class LoaderAdapter implements GameStateLoader {
    private final FileConverter fileConverter;
    private final TxtLoader txtLoader;

    public LoaderAdapter(FileConverter fileConverter) {
        this.fileConverter = fileConverter;
        this.txtLoader = new TxtLoader();
    }

    @Override
    public void loadGameState(String folderPath) {
        fileConverter.convertToTxt(folderPath);
        txtLoader.loadGameState(folderPath);
    }

    @Override
    public void saveGameState(String folderPath) {
        txtLoader.saveGameState(folderPath);
        fileConverter.convertFromTxt(folderPath);
    }

    @Override
    public String getSupportedFileExtension() {
        return fileConverter.supportedExtension();
    }
}
