package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import app.loader.LoaderManager;

import java.io.File;

public class PluginDialogController {
    @FXML
    private TextField folderField;
    @FXML
    private Label statusLabel;
    private File selectedFile;

    @FXML
    protected void handleChooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Plugin File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JAR Files (*.jar)", "*.jar"));
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            folderField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void handleUpload() {
        if (selectedFile != null &&
                folderField.getText().equals(selectedFile.getAbsolutePath()) &&
                selectedFile.getName().endsWith(".jar")) {
            LoaderManager lm = LoaderManager.getInstance();
            boolean loaded = lm.loadLoaderFromJar(selectedFile.getAbsolutePath());
            if (loaded) {
                statusLabel.setText("Success!");
                ((Stage) statusLabel.getScene().getWindow()).close();
            } else {
                statusLabel.setText("Failed: Error loading plugin from JAR.");
            }
        } else {
            statusLabel.setText("Failed: Please select a valid JAR file.");
        }
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}
