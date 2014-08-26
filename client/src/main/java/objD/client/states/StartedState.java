package objD.client.states;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import objD.client.ClientApp;
import objD.client.HexagonHelper;
import objD.client.SocketAdapter;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StartedState implements ClientState {

    private static final Logger LOG = LoggerFactory.getLogger(StartedState.class);

    private final ClientApp clientApp;
    private final SocketAdapter socketAdapter;
    private final GridPane pane = new GridPane();
    private final Group desk = new Group();

    public StartedState(ClientApp clientApp, SocketAdapter socketAdapter) {
        this.clientApp = clientApp;
        this.socketAdapter = socketAdapter;


        List<Polyline> hexagonGrid = HexagonHelper.getHexagonGrid(50, 50, 30, 7, 3);


        desk.getChildren().addAll(hexagonGrid);

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
