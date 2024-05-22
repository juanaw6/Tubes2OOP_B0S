package jsonplugin;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
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
        Map<String, Object> jsonData = mapper.readValue(new File(filePath), Map.class);

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
                System.out.println(name + " " + quantity);
                writer.newLine();
            }
        }
    }

    private void convertPlayerToTxt(String filePath, String outputFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Read JSON content from the file
        Map<String, Object> jsonData = mapper.readValue(new File(filePath), Map.class);

        int gulden = (int) jsonData.get("gulden");
        int deck = (int) jsonData.get("deck");
        List<Map<String, Object>> activeDeck = (List<Map<String, Object>>) jsonData.get("active_deck");
        List<Map<String, Object>> field = (List<Map<String, Object>>) jsonData.get("field");

        // Write content to the TXT file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(String.valueOf(gulden));
            writer.newLine();
            writer.write(String.valueOf(deck));
            writer.newLine();

            writer.write(String.valueOf(activeDeck.size()));
            writer.newLine();
            for (Map<String, Object> card : activeDeck) {
                String location = (String) card.get("location");
                String cardType = (String) card.get("card_type");
                writer.write(location + " " + cardType);
                writer.newLine();
            }

            writer.write(String.valueOf(field.size()));
            writer.newLine();
            for (Map<String, Object> card : field) {
                String location = (String) card.get("location");
                String cardType = (String) card.get("card_type");
                int ageOrWeight = (int) card.get("age_or_weight");
                List<String> effects = (List<String>) card.get("effects");
                writer.write(location + " " + cardType + " " + ageOrWeight + " " + effects.size());
                for (String effect : effects) {
                    writer.write(" " + effect);
                }
                writer.newLine();
            }
        }
    }

    private void convertGamestateFromTxt(String filePath, String outputFilePath) throws IOException {
        // Implement the conversion from TXT to JSON if needed
    }

    private void convertPlayerFromTxt(String filePath, String outputFilePath) throws IOException {
        // Implement the conversion from TXT to JSON if needed
    }
}