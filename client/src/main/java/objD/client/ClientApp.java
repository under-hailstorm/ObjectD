package objD.client;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import objD.client.states.ClientState;
import objD.client.states.ConnectionState;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(ClientApp.class);

    public static final String APP_NAME = "Cool app ";
    private Queue<ServerMessage> actionsQueue = new ConcurrentLinkedQueue<>();
    private Scene scene;
    private volatile ClientState currentState;
    private Stage primaryStage;
    private String clientName;

    public Queue<ServerMessage> getActionsQueue() {
        return actionsQueue;
    }

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


        Timeline applyServerMessages = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                currentState.updatePane();
            }
        }));
        applyServerMessages.setCycleCount(Timeline.INDEFINITE);
        applyServerMessages.play();

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
