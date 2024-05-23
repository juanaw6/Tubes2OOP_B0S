package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import tubes2oop_b0s.loader.LoaderManager;

import java.io.File;
import java.util.ArrayList;

public class LoadDialogController {
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
    public void populateFileTypeComboBox() {
        LoaderManager lm = LoaderManager.getInstance();
        ArrayList<String> supportedExtensions = lm.getSupportedFileExtensions();
        fileTypeComboBox.getItems().addAll(supportedExtensions);
    }

    @FXML
    protected void handleChooseFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Folder");
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            folderField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    protected void handleLoad() {
        String folder = folderField.getText();
        String fileType = fileTypeComboBox.getValue();

        if (folder.isEmpty() || fileType == null) {
            statusLabel.setText("Failed: Please select a folder and file type.");
            return;
        }

        try {
            // Implement your file loading logic here
            System.out.println("File loaded from: " + folder);
            LoaderManager lm = LoaderManager.getInstance();
            lm.loadGameState(folder, fileType);
            statusLabel.setText("Success!");
            // Close the dialog after loading
            ((Stage) statusLabel.getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Failed: Error loading file.");
        }
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}
