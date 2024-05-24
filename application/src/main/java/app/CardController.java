package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CardController {
    @FXML
    private VBox card;

    private boolean isView;

    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardLabel;

    @FXML
    public void initialize() {
        // Uncomment if needed to make the card draggable immediately
        // makeDraggable();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web("#0000001A"));
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(6);
        dropShadow.setRadius(10);
        dropShadow.setSpread(0.5);

        // Apply the effect to the VBox
        card.setEffect(dropShadow);
    }

    public ImageView getCardImage() {
        return cardImage;
    }

    public void setAttackedCard() {
        // Set drop shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.web("#FF0000")); // Red color for the shadow
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(6);
        dropShadow.setRadius(10);
        dropShadow.setSpread(0.5);

        // Apply the drop shadow effect
        card.setEffect(dropShadow);
    }

    public void setSmallCard() {
        card.setPrefSize(86, 56);
    }

    public void setCardInfo(String id, String imageName, String cardName, boolean isView) {
        Image image = new Image(getClass().getResourceAsStream("/public/" + imageName));
        cardImage.setImage(image);
        cardLabel.setText(cardName);
        card.setId(id);
        card.setOnDragDropped(this::onDragDropped);
        card.setOnDragOver(this::onDragOver);
        if (!isView) {
            card.setOnDragDetected(this::onDragDetected);
        }
        this.isView = isView;
        if ((id.contains("farm") || id.contains("deck")) && !cardName.isEmpty()) {
            card.setOnMouseClicked(this::handleCardClick);
        }
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
        MainData maindata = MainData.getInstance();

        if (db.hasString()) {
            VBox sourceVBox = (VBox) event.getGestureSource();
            VBox targetVBox = (VBox) event.getGestureTarget();

            System.out.println("Dragged from: " + sourceVBox.getId());
            System.out.println("Dropped on: " + targetVBox.getId());
            maindata.DragDropCard(sourceVBox.getId(), targetVBox.getId(), event);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        try {
            System.out.println(card.getId());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/dialog_card.fxml"));
            Parent dialog = loader.load();
            Scene scene = new Scene(dialog);
            DialogCardController controller = loader.getController();
            controller.setDialogCardPaneId("detail-" + card.getId(), isView);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Card Details");
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
