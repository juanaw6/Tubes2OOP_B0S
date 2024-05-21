package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogCardController {
    @FXML
    private VBox dialogCardPane;
    
    @FXML
    public void initialize() {
        
    }

    @FXML
    private Button closeButton;  // Ensure this button is linked if you are using Scene Builder or add the fx:id in the FXML.

    @FXML
    private void closeDialog(MouseEvent event) {
        // Get the stage from the button that is part of the dialog.
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    public void setDialogCardPaneId(String id) {
      dialogCardPane.setId(id); 
    }
}
