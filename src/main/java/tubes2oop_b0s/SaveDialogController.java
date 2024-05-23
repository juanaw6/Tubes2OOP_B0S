package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import tubes2oop_b0s.loader.LoaderManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveDialogController {
    @FXML
    private ComboBox<String> fileTypeComboBox;
    @FXML
    private TextField folderField;
    @FXML
    private Label statusLabel;

    public void initialize() {
        populateFileTypeComboBox();
    }

    @FXML
    protected void handleChooseFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            folderField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void populateFileTypeComboBox() {
        LoaderManager lm = LoaderManager.getInstance();
        ArrayList<String> supportedExtensions = lm.getSupportedFileExtensions();
        fileTypeComboBox.getItems().addAll(supportedExtensions);
    }

    @FXML
    protected void handleSave() {
        String fileType = fileTypeComboBox.getValue();
        String folder = folderField.getText();
        if (folder.isEmpty()) {
            statusLabel.setText("Failed: Please select a folder and file type.");
            return;
        }
        if (fileType == null) {
            fileType = "txt";
        }

        try {
            LoaderManager lm = LoaderManager.getInstance();
            lm.saveGameState(folder, fileType);
            statusLabel.setText("Success!");
            ((Stage) statusLabel.getScene().getWindow()).close();
        } catch (Exception e) {
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
