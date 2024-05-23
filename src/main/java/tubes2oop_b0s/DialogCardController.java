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
import tubes2oop_b0s.card.ConsumableCard;
import tubes2oop_b0s.card.animals.Animal;
import tubes2oop_b0s.card.crops.Crop;
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
        
//        get index and get the type
        if (!isView) {
            System.out.println(input);
            switch (parts[1]) {
                case "farm" -> {
                    String name = gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number).getName();
                    Image image = new Image(getClass().getResourceAsStream("/public/" + name + ".png"));
                    detailCardImage.setImage(image);
                    detailCardLabel.setText(name);
                    if (gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number) instanceof Animal) {
                        Animal animal = ((Animal) gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number));
                        detailCard.setText("Weight: "+animal.getWeight() + " ("+ animal.getHarvestWeight() + ")");
                        buttonAction.setDisable(!animal.isReadyToHarvest());
                        
                    }else if (gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number) instanceof Crop){
                        Crop crop = (Crop) gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number);
                        detailCard.setText("Age: "+crop.getAge() + " ("+ crop.getHarvestAge() + ")");
                        if (crop.isReadyToHarvest()) {
                            image = new Image(getClass().getResourceAsStream("/public/" + name.replace("Biji ", "") + ".png"));
                            detailCardImage.setImage(image);
                        }
                        buttonAction.setDisable(!crop.isReadyToHarvest());
                    }
                    detailCardEffect.setText("Item aktif: "+convertMapToString(gameState.getCurrentPlayer().getFieldRef().getFieldRef().get(number).getEffects()));
                    buttonAction.setText("Harvest");
                }
                case "deck" -> {
                    String name = gameState.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(number).getName();
                    Image image = new Image(getClass().getResourceAsStream("/public/" + name + ".png"));
                    detailCardImage.setImage(image);
                    detailCardLabel.setText(name);
                    detailCard.setText("Price: "+String.valueOf(((ConsumableCard) gameState.getCurrentPlayer().getDeckRef().getActiveDeckRef().get(number)).getPrice()));
                    buttonAction.setText("Sell");
                }
            }
        }else {
            buttonAction.setVisible(false);
        }
    }
    
    @FXML
    protected void OnClickButton(ActionEvent event){
        String input = dialogCardPane.getId();

        String[] parts = input.split("-");
        int numberId = 0;
        try {
            numberId = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            System.out.println("The third part of the string is not a valid integer.");
        }
        MainData mainData = MainData.getInstance();
//        do someting about the data
            System.out.println(dialogCardPane.getId());
            System.out.println(buttonAction.getText());
        switch (buttonAction.getText()) {
            case "Harvest" -> {
                mainData.onHarvest(numberId);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
            case "Sell" -> {
                mainData.onSell(numberId);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
            
        }
    }
    
}
