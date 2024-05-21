package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveDialogController {
    @FXML private ComboBox<String> fileTypeComboBox;
    @FXML private TextField folderField;
    @FXML private Label statusLabel;

    @FXML
    protected void handleSave() {
        String fileType = fileTypeComboBox.getValue();
        String folder = folderField.getText();
        String filePath = folder + "/" + "savefile" + fileType;
        // Implement your file saving logic here
        System.out.println("File saved to: " + filePath);
        statusLabel.setText("Success!");
        // Close the dialog after saving
        ((Stage) statusLabel.getScene().getWindow()).close();
    }

    @FXML
    protected void handleBack() {
        // Close the dialog
        ((Stage) folderField.getScene().getWindow()).close();
    }
}