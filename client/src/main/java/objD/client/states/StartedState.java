package objD.client.states;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import objD.client.ClientApp;
import objD.client.SocketAdapter;
import objD.protocol.server.ServerMessage;

public class StartedState implements ClientState {

    private final ClientApp clientApp;
    private final SocketAdapter socketAdapter;
    private final GridPane pane = new GridPane();
    private final Group desk = new Group();

    public StartedState(ClientApp clientApp, SocketAdapter socketAdapter) {
        this.clientApp = clientApp;
        this.socketAdapter = socketAdapter;


        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[]{
                0.0, 0.0,
                20.0, 10.0,
                10.0, 20.0});


        desk.getChildren().add(polygon);

        pane.add(desk, 0, 0);
    }

    @Override
    public Pane getRootPane() {


        return pane;
    }

    @Override
    public void updatePane(ServerMessage fromServer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
