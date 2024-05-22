package api;

public interface FileConverter {
    void convertToTxt(String folderPath);
    void convertFromTxt(String folderPath);
    String supportedExtension();
}
