package objD.client;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objD.client.states.ClientState;
import objD.client.states.ConnectionState;

public class ClientApp extends Application {

    public static final String APP_NAME = "Cool app ";
    private Scene scene;
    private ClientState currentState;
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
    }

    public void notifyState(Object o) {
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
        launch(args);
    }
}
