package yamlplugin;

import api.FileConverter;

import java.io.*;

public class YamlConverter implements FileConverter {

    @Override
    public void convertToTxt(String folderPath) {
        try {
            convertGamestateToTxt(folderPath + "/gamestate.json", folderPath + "/gamestate.txt");
            convertPlayerToTxt(folderPath + "/player1.json", folderPath + "/player1.txt");
            convertPlayerToTxt(folderPath + "/player2.json", folderPath + "/player2.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void convertFromTxt(String folderPath) {
        try {
            convertGamestateFromTxt(folderPath + "/gamestate.txt", folderPath + "/gamestate.json");
            convertPlayerFromTxt(folderPath + "/player1.txt", folderPath + "/player1.json");
            convertPlayerFromTxt(folderPath + "/player2.txt", folderPath + "/player2.json");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String supportedExtension() {
        return "yaml";
    }

    private void convertGamestateToTxt(String filePath, String outputFilePath) throws IOException {

    }

    private void convertPlayerToTxt(String filePath, String outputFilePath) throws IOException {

    }

    private void convertGamestateFromTxt(String filePath, String outputFilePath) throws IOException {

    }

    private void convertPlayerFromTxt(String filePath, String outputFilePath) throws IOException {

    }
}
