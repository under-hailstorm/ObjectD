package objD.client.ui;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;

public class Tank extends Parent {

    public Tank() {
        ImageView imageView = new ImageView();
        imageView.setImage(ImagesHolder.getInstance().getImage("tank"));
        getChildren().add(imageView);

    }
}
