package jsonplugin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import api.FileConverter;

public class JsonConverter implements FileConverter {

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
        return "json";
    }

    private void convertGamestateToTxt(String filePath, String outputFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Read JSON content from the file
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }

        String jsonString = sb.toString().trim(); // Remove leading/trailing whitespace

        // Parse the JSON string into a Java object (Map in this case)
        Map jsonData = mapper.readValue(jsonString, Map.class);

        int turn = (int) jsonData.get("turn");
        List<Map<String, Object>> store = (List<Map<String, Object>>) jsonData.get("store");

        // Write content to the TXT file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(String.valueOf(turn));
            writer.newLine();
            writer.write(String.valueOf(store.size()));
            writer.newLine();

            for (Map<String, Object> item : store) {
                String name = (String) item.get("name");
                int quantity = (int) item.get("quantity");
                writer.write(name + " " + quantity);
                writer.newLine();
            }
        }
    }

    private void convertPlayerToTxt(String filePath, String outputFilePath) throws IOException {

    }

    private void convertGamestateFromTxt(String filePath, String outputFilePath) throws IOException {

    }

    private void convertPlayerFromTxt(String filePath, String outputFilePath) throws IOException {

    }
}
