package yamlplugin;

import api.FileConverter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class YamlConverter implements FileConverter {

    @Override
    public void convertToTxt(String folderPath) {
        try {
            convertGamestateToTxt(folderPath + "/gamestate.yaml", folderPath + "/gamestate.txt");
            convertPlayerToTxt(folderPath + "/player1.yaml", folderPath + "/player1.txt");
            convertPlayerToTxt(folderPath + "/player2.yaml", folderPath + "/player2.txt");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void convertFromTxt(String folderPath) {
        try {
            convertGamestateFromTxt(folderPath + "/gamestate.txt", folderPath + "/gamestate.yaml");
            convertPlayerFromTxt(folderPath + "/player1.txt", folderPath + "/player1.yaml");
            convertPlayerFromTxt(folderPath + "/player2.txt", folderPath + "/player2.yaml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String supportedExtension() {
        return "yaml";
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

    private void convertGamestateToTxt(String filePath, String outputFilePath) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (InputStream inputStream = new FileInputStream(new File(filePath))) {
            data = yaml.load(inputStream);
        }

        int turn = (int) data.get("turn");
        List<Map<String, Object>> store = (List<Map<String, Object>>) data.get("store");

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
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try (InputStream inputStream = new FileInputStream(new File(filePath))) {
            data = yaml.load(inputStream);
        }

        int gulden = (int) data.get("gulden");
        int deck = (int) data.get("deck");
        List<Map<String, Object>> activeDeck = (List<Map<String, Object>>) data.get("active_deck");
        List<Map<String, Object>> field = (List<Map<String, Object>>) data.get("field");

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
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Map<String, Object> data = new LinkedHashMap<>();
        List<Map<String, Object>> store = new ArrayList<>();

        int turn = Integer.parseInt(reader.readLine().trim());
        int storeSize = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < storeSize; i++) {
            String[] parts = reader.readLine().split(" ");
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("name", parts[0]);
            item.put("quantity", Integer.parseInt(parts[1]));
            store.add(item);
        }

        reader.close();

        data.put("turn", turn);
        data.put("store", store);

        Yaml yaml = new Yaml();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            yaml.dump(data, writer);
        }
    }

    private void convertPlayerFromTxt(String filePath, String outputFilePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        Map<String, Object> data = new LinkedHashMap<>();
        List<Map<String, Object>> activeDeck = new ArrayList<>();
        List<Map<String, Object>> field = new ArrayList<>();

        int gulden = Integer.parseInt(reader.readLine().trim());
        int deck = Integer.parseInt(reader.readLine().trim());
        int activeDeckSize = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < activeDeckSize; i++) {
            String[] parts = reader.readLine().split(" ");
            Map<String, Object> card = new LinkedHashMap<>();
            card.put("location", parts[0]);
            card.put("card_type", parts[1]);
            activeDeck.add(card);
        }

        int fieldSize = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < fieldSize; i++) {
            String[] parts = reader.readLine().split(" ");
            Map<String, Object> card = new LinkedHashMap<>();
            card.put("location", parts[0]);
            card.put("card_type", parts[1]);
            card.put("age_or_weight", Integer.parseInt(parts[2]));
            int effectsSize = Integer.parseInt(parts[3]);
            List<String> effects = new ArrayList<>();
            for (int j = 0; j < effectsSize; j++) {
                effects.add(parts[4 + j]);
            }
            card.put("effects", effects);
            field.add(card);
        }

        reader.close();

        data.put("gulden", gulden);
        data.put("deck", deck);
        data.put("active_deck", activeDeck);
        data.put("field", field);

        Yaml yaml = new Yaml();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            yaml.dump(data, writer);
        }
    }
}