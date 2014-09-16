package objD.client.ui.map;

import javafx.scene.Parent;

public class UIMap extends Parent {

    private static final double HEX_SIDE = 50;

    private final int rowNum;
    private final int colNum;

    public UIMap(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
}
