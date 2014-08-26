package objD.client;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objD.client.states.ClientState;
import objD.client.states.ConnectionState;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(ClientApp.class);

    public static final String APP_NAME = "Cool app ";
    private Scene scene;
    private volatile ClientState currentState;
    private Stage primaryStage;
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setCurrentState(ClientState state) {
        scene.setRoot(state.getRootPane());
        scene.getRoot().requestLayout();
        currentState = state;
    }

    public void notifyState(ServerMessage o) {
        LOG.debug("notify " + currentState.getClass().getCanonicalName() + " with " + o.getClass().getCanonicalName());
        currentState.updatePane(o);
    }

    public void setTitle(String title) {
        primaryStage.setTitle(title);
    }

    public void setWindowSize(double width, double heigth) {
        primaryStage.setMinWidth(width);
        primaryStage.setMinHeight(heigth);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        currentState = new ConnectionState(this);
        scene = new Scene(currentState.getRootPane(), 300, 250);
        scene.getStylesheets().add("client.css");

        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application. main() serves only as fallback in case the
     * application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support. NetBeans
     * ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOG.debug("starting client app...");
        launch(args);
    }
}
