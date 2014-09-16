package objD.client.ui.map;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import objD.client.ui.ImagesHolder;

public class UIMapEntry extends Parent {
    private final int rowNum;
    private final int colNum;
    private final UIMapEntryType type;
    private final double hexSide;

    private int getRowNum() {
        return rowNum;
    }

    private int getColNum() {
        return colNum;
    }

    private UIMapEntryType getType() {
        return type;
    }

    private UIMapEntry(int rowNum, int colNum, UIMapEntryType type, double hexSide) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.type = type;
        this.hexSide = hexSide;

        ImageView imageView = new ImageView();
        imageView.setImage(ImagesHolder.getInstance().getImage(type.getImageKey()));

        getChildren().add(imageView);
    }
}
