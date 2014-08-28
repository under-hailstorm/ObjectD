package objD.client.ui;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class Tank extends Parent {

    private static final double TANK_SIZE = 50;

    public Tank() {
        ImageView imageView = new ImageView();
        imageView.setImage(ImagesHolder.getInstance().getImage("tank"));
        imageView.setFitWidth(TANK_SIZE);
        imageView.setFitHeight(TANK_SIZE);
        getChildren().add(imageView);

    }
}
