package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            folderField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void handleUpload() {
        if (selectedFile != null && folderField.getText().equals(selectedFile.getAbsolutePath())) {
            // Implement your file upload logic here
            System.out.println("File uploaded: " + selectedFile.getAbsolutePath());
            statusLabel.setText("Success!");
            // Close the dialog after uploading
            ((Stage) statusLabel.getScene().getWindow()).close();
        } else {
            statusLabel.setText("Failed");
        }
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}
