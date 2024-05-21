package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveDialogController {
    @FXML
    private ComboBox<String> fileTypeComboBox;
    @FXML
    private TextField folderField;
    @FXML
    private Label statusLabel;

    @FXML
    protected void handleChooseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            folderField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    // @FXML
    // protected void handleSave() {
    // String fileType = fileTypeComboBox.getValue();
    // String folder = folderField.getText();
    // String filePath = folder + "/" + "savefile" + fileType;
    // // Implement your file saving logic here
    // System.out.println("File saved to: " + filePath);
    // statusLabel.setText("Success!");
    // // Close the dialog after saving
    // ((Stage) statusLabel.getScene().getWindow()).close();
    // }

    @FXML
    protected void handleSave() {
        String fileType = fileTypeComboBox.getValue();
        String folder = folderField.getText();
        if (fileType == null || folder.isEmpty()) {
            statusLabel.setText("Failed: Please select a file type and folder.");
            return;
        }

        String filePath = folder + "/" + "savefile" + fileType;
        try (FileWriter fileWriter = new FileWriter(new File(filePath))) {
            // Dummy content to save in the file
            String dummyContent = "This is a dummy content for the save file.";
            fileWriter.write(dummyContent);
            statusLabel.setText("Success!");
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Failed: Error saving file.");
        }
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}
