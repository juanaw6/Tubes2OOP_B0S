package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tubes2oop_b0s.card.Card;

public class DialogCardController {
    @FXML
    private VBox dialogCardPane;
    
    @FXML
    private Label detailCardLabel;
    
    @FXML
    private Label detailCard;
    @FXML
    private Label detailCardEffect;
    @FXML
    private Image detailCardImage;
    @FXML
    private Button buttonAction;
    
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
        
    }
    
    public void setDialogCardPaneId(String id, boolean isView) {
        dialogCardPane.setId(id);
        String input = id;

        String[] parts = input.split("-");
        int number = 0;
        try {
            // Attempt to parse the third part as an integer
            number = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("The third part of the string is not a valid integer.");
        }
        
//        get index and get the type
        if (!isView) {
            System.out.println(input);
            switch (parts[1]) {
                case "farm" -> {
                    buttonAction.setDisable(true);
                    buttonAction.setText("Harvest");
                }
                case "deck" -> {
                    buttonAction.setDisable(true);
                    buttonAction.setText("Sell");
                }
                case "store" -> {
                    buttonAction.setDisable(false);
                    buttonAction.setText("Buy");
                }
            }
        }else {
            buttonAction.setVisible(false);
        }
    }
    
    @FXML
    protected void OnClickButton(ActionEvent event){
        MainData mainData = MainData.getInstance();
//        do someting about the data
            System.out.println(dialogCardPane.getId());
            System.out.println(buttonAction.getText());
        switch (buttonAction.getText()) {
            case "Harvest" -> {
                buttonAction.setDisable(true);
                buttonAction.setText("Harvest");
            }
            case "Sell" -> {
            }
            case "Buy" -> {
            }
        }
    }
    
}
