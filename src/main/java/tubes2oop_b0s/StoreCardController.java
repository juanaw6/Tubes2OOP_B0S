package tubes2oop_b0s;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.awt.event.ActionEvent;
import java.util.spi.CalendarDataProvider;

public class StoreCardController {
    @FXML
    private VBox card;

    private boolean isView;

    @FXML
    private ImageView cardImage;
    @FXML
    private Label cardLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label stockLabel;

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

    public void setCardInfo(String id, String imageName, String cardName, int price, int stock ) {
        Image image = new Image(getClass().getResourceAsStream("/public/" + imageName));
        cardImage.setImage(image);
        cardLabel.setText(cardName);
        priceLabel.setText("Price: " + price);
        stockLabel.setText("Stock: " + stock);
        card.setId(id);
    }
    public  void  onBuy(ActionEvent event) {
        MainData mainData = MainData.getInstance();
        String[] targetparts = card.getId().split("-");
        mainData.onBuy(targetparts[1]);
    }
}


   
