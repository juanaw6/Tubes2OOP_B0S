package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadDialogController {
    @FXML
    private ComboBox<String> fileTypeComboBox;
    @FXML
    private TextField folderField;
    @FXML
    private Label statusLabel;

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
        String fileType = fileTypeComboBox.getValue();
        String folder = folderField.getText();
        String filePath = folder + "/" + "savefile" + fileType;
        // Implement your file loading logic here
        System.out.println("File loaded from: " + filePath);
        statusLabel.setText("Success!");
        // Close the dialog after loading
        ((Stage) statusLabel.getScene().getWindow()).close();
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}
