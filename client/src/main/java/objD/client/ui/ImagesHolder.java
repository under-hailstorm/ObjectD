package objD.client.ui;

import javafx.scene.image.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ImagesHolder {
    private static final Logger LOG = LoggerFactory.getLogger(ImagesHolder.class);
    private static ImagesHolder instance;

    private static final String IMG_DIR = "/img";
    private final Map<String, Image> loadedImages = new HashMap<>();

    public static ImagesHolder getInstance() {
        if (instance == null) {
            instance = new ImagesHolder();
        }
        return instance;
    }

    private ImagesHolder() {
        addIfExists("tank", "tank.png");
    }


    private void addIfExists(String alias, String fileName) {
        Image image = new Image(getClass().getResourceAsStream(IMG_DIR + "/" + fileName));
        if (image.isError()) {
            LOG.warn("Image " + IMG_DIR + "/" + fileName + " not found");
        } else {
            loadedImages.put(alias, image);
        }
    }

    public Image getImage(String alias) {
        return loadedImages.get(alias);
    }
}
