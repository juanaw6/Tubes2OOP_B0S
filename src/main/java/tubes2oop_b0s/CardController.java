package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.spi.CalendarDataProvider;

public class CardController {
    @FXML
    private VBox card;
    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardLabel;

    @FXML
    public void initialize() {
        // Uncomment if needed to make the card draggable immediately
        // makeDraggable();
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setCardInfo(String id, String imageName, String cardName) {
        Image image = new Image(getClass().getResourceAsStream("/public/" + imageName));
        cardImage.setImage(image);
        cardLabel.setText(cardName);
        card.setId(id);
        card.setOnDragOver(this::onDragOver);
        card.setOnDragDropped(this::onDragDropped);
        card.setOnDragDetected(this::onDragDetected);
        card.setOnMouseClicked(this::handleCardClick);
    }

    @FXML
    protected void onDragOver(DragEvent event) {
        if (event.getGestureSource() != event.getSource() && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    @FXML
    protected void onDragDetected(MouseEvent event) {
        VBox vBox = (VBox) event.getSource();
        Dragboard db = vBox.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.putString(vBox.getId());
        db.setContent(content);

        WritableImage image = vBox.snapshot(new SnapshotParameters(), null);
        db.setDragView(image, event.getX(), event.getY());

        event.consume();
    }

    @FXML
    protected void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            VBox sourceVBox = (VBox) event.getGestureSource();
            VBox targetVBox = (VBox) event.getGestureTarget();

            System.out.println("Dragged from: " + sourceVBox.getId());
            System.out.println("Dropped on: " + targetVBox.getId());

            // Implement your logic for handling the dropped data
            // For example, you could swap the contents of the source and target VBox

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    private void handleCardClick(MouseEvent event) {
        try {
            System.out.println(card.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dialogCard.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            DialogCardController controller = loader.getController();
            controller.setDialogCardPaneId("p"+((Node)event.getSource()).getId());
            
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Card Details");
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setOpacity(0.9);
            dialogStage.initOwner(((Node)event.getSource()).getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
