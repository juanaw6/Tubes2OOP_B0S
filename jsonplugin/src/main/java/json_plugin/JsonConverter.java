package json_plugin;

import api.FileConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
    public void cleanSupportedExtensionFiles(String folderPath) {
        cleanFilesByNames(folderPath, supportedExtension());
    }

    @Override
    public void cleanTxtFiles(String folderPath) {
        cleanFilesByNames(folderPath, "txt");
    }

    private void cleanFilesByNames(String folderPath, String extension) {
        List<String> fileNames = Arrays.asList("gamestate", "player1", "player2");
        fileNames.forEach(fileName -> {
            String filePath = folderPath + "/" + fileName + "." + extension;
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                System.err.println("Error deleting file: " + filePath + " - " + e.getMessage());
            }
        });
    }

    @Override
    public String supportedExtension() {
        return "json";
    }

    private void convertGamestateToTxt(String filePath, String outputFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> jsonData = mapper.readValue(new File(filePath), Map.class);

        int turn = (int) jsonData.get("turn");
        List<Map<String, Object>> store = (List<Map<String, Object>>) jsonData.get("store");

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
        ObjectMapper mapper = new ObjectMapper();

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> jsonData = new HashMap<>();
        List<Map<String, Object>> store = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            int turn = Integer.parseInt(line);
            jsonData.put("turn", turn);

            line = reader.readLine();
            int storeSize = Integer.parseInt(line);

            for (int i = 0; i < storeSize; i++) {
                line = reader.readLine();
                String[] parts = line.split(" ");
                String name = parts[0];
                int quantity = Integer.parseInt(parts[1]);
                Map<String, Object> item = new HashMap<>();
                item.put("name", name);
                item.put("quantity", quantity);
                store.add(item);
            }
        }

        jsonData.put("store", store);
        mapper.writeValue(new File(outputFilePath), jsonData);
    }

    private void convertPlayerFromTxt(String filePath, String outputFilePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, Object> jsonData = new HashMap<>();
        List<Map<String, Object>> activeDeck = new ArrayList<>();
        List<Map<String, Object>> field = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            int gulden = Integer.parseInt(line);
            jsonData.put("gulden", gulden);

            line = reader.readLine();
            int deck = Integer.parseInt(line);
            jsonData.put("deck", deck);

            line = reader.readLine();
            int activeDeckSize = Integer.parseInt(line);

            for (int i = 0; i < activeDeckSize; i++) {
                line = reader.readLine();
                String[] parts = line.split(" ");
                String location = parts[0];
                String cardType = parts[1];
                Map<String, Object> card = new HashMap<>();
                card.put("location", location);
                card.put("card_type", cardType);
                activeDeck.add(card);
            }

            line = reader.readLine();
            int fieldSize = Integer.parseInt(line);

            for (int i = 0; i < fieldSize; i++) {
                line = reader.readLine();
                String[] parts = line.split(" ");
                String location = parts[0];
                String cardType = parts[1];
                int ageOrWeight = Integer.parseInt(parts[2]);
                int effectsSize = Integer.parseInt(parts[3]);
                List<String> effects = new ArrayList<>();
                for (int j = 0; j < effectsSize; j++) {
                    effects.add(parts[4 + j]);
                }
                Map<String, Object> card = new HashMap<>();
                card.put("location", location);
                card.put("card_type", cardType);
                card.put("age_or_weight", ageOrWeight);
                card.put("effects", effects);
                field.add(card);
            }
        }

        jsonData.put("active_deck", activeDeck);
        jsonData.put("field", field);
        mapper.writeValue(new File(outputFilePath), jsonData);
    }
}