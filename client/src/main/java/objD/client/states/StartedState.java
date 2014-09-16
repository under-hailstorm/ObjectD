package objD.client.states;

import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import objD.client.ClientApp;
import objD.client.HexagonHelper;
import objD.client.SocketAdapter;
import objD.client.ui.UITank;
import objD.model.GMap;
import objD.protocol.server.FullMap;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class StartedState implements ClientState {

    private static final Logger LOG = LoggerFactory.getLogger(StartedState.class);

    private final ClientApp clientApp;
    private final Queue<ServerMessage> actionsQueue;
    private final SocketAdapter socketAdapter;
    private final GridPane pane = new GridPane();
    private final Group desk = new Group();

    public StartedState(ClientApp clientApp, SocketAdapter socketAdapter) {
        this.clientApp = clientApp;
        this.actionsQueue = this.clientApp.getActionsQueue();
        this.socketAdapter = socketAdapter;


        List<Polyline> hexagonGrid = HexagonHelper.getHexagonGrid(50, 50, 30, 7, 6);
        UITank tank = new UITank();

        desk.getChildren().addAll(hexagonGrid);

        desk.getChildren().add(tank);

        pane.add(desk, 0, 0);
    }

    @Override
    public Pane getRootPane() {
        return pane;
    }

    @Override
    public void updatePane() {
        int size = actionsQueue.size();
        List<ServerMessage> toProcess = new ArrayList<>();
        //trim all before fullMap if it exists
        for (int i = 0; i < size; i++) {
            ServerMessage poll = actionsQueue.poll();
            if (poll instanceof FullMap) {
                toProcess = new ArrayList<>();
            }
            toProcess.add(poll);
        }
        for(ServerMessage mesage: toProcess){

        }
    }
}
