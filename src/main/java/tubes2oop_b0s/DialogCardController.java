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
    private Button closeButton;  // Ensure this button is linked if you are using Scene Builder or add the fx:id in the FXML.
    
    @FXML
    public void initialize() {
        closeButton.setOnMouseClicked(event -> {
            System.out.println("Mouse clicked");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            // Your existing handler code
        });
        closeButton.setOnMousePressed(event -> {
            System.out.println("Mouse pressed on closeButton");
        });

        closeButton.setOnMouseReleased(event -> {
            System.out.println("Mouse released on button");
            // Perform action here
        });
    }


//    @FXML
//    private void closeDialog(MouseEvent event) {
//        // Get the stage from the button that is part of the dialog.
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.close();
//    }
    
    public void setDialogCardPaneId(String id) {
      dialogCardPane.setId(id); 
    }
}
