package objD.client;

import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

public class HexagonHelper {

    private static final double COS_60 = 0.5;
    private static final double SIN_60 = Math.sin(Math.PI / 3);

    public static Polyline getHexagon(double xCenter, double yCenter, double sideLenfth) {
        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(xCenter - COS_60 * sideLenfth, yCenter - SIN_60 * sideLenfth);
        polyline.getPoints().addAll(xCenter + COS_60 * sideLenfth, yCenter - SIN_60 * sideLenfth);
        polyline.getPoints().addAll(xCenter + sideLenfth, yCenter);
        polyline.getPoints().addAll(xCenter + COS_60 * sideLenfth, yCenter + SIN_60 * sideLenfth);
        polyline.getPoints().addAll(xCenter - COS_60 * sideLenfth, yCenter + SIN_60 * sideLenfth);
        polyline.getPoints().addAll(xCenter - sideLenfth, yCenter);
        polyline.getPoints().addAll(xCenter - COS_60 * sideLenfth, yCenter - SIN_60 * sideLenfth);

        return polyline;
    }

    public static List<Polyline> getHexagonGrid(double xCorner, double yCorner, double sideLenfth, int width, int heigth) {
        List<Polyline> polylines = new ArrayList<>();
        for (int i = 0; i < heigth; i++) {
            int actualWidth = (i % 2 == 0) ? width - 1 : width;
            double firstXCenter = (i % 2 == 0) ? xCorner + 1.5 * sideLenfth : xCorner;
            double yCenter = yCorner + i * sideLenfth * SIN_60;
            for (int j = 0; j < actualWidth; j++) {
                Polyline hexagon = getHexagon(firstXCenter + 3 * sideLenfth * j, yCenter, sideLenfth);
                polylines.add(hexagon);
            }
        }
        return polylines;
    }

}
