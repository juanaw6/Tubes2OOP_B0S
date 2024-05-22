package tubes2oop_b0s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tubes2oop_b0s.card.Card;
import tubes2oop_b0s.state.GameState;
import java.util.HashMap;
import java.util.Map;

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
    private ImageView detailCardImage;
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
    public String convertMapToString(HashMap<String, Integer> map) {
        StringBuilder builder = new StringBuilder();

        // Iterate over the map entries
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (!builder.isEmpty()) {
                builder.append(", ");  // append a comma only after the first entry
            }
            builder.append(entry.getKey())        // append the key
                    .append("(")
                    .append(entry.getValue())     // append the value
                    .append(")");
        }

        return builder.toString();
    }
    
    public void setDialogCardPaneId(String id, boolean isView) {
        GameState gameState = GameState.getInstance();
        dialogCardPane.setId(id);
        String input = id;

        String[] parts = input.split("-");
        int number = 0;
        try {
            number = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("The third part of the string is not a valid integer.");
        }
        String name = gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number).getName();
        Image image = new Image(getClass().getResourceAsStream("/public/" + name + ".png"));
        detailCardImage.setImage(image);
        detailCardLabel.setText(name);
        
//        get index and get the type
        if (!isView) {
            System.out.println(input);
            switch (parts[1]) {
                case "farm" -> {
                    detailCardEffect.setText(convertMapToString(gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number).getEffects()));
                    buttonAction.setDisable(true);
                    buttonAction.setText("Harvest");
                }
                case "deck" -> {
//                    detailCardEffect.setText(convertMapToString(gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number).);
                    buttonAction.setDisable(true);
                    buttonAction.setText("Sell");
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
