package objD.client.states;

import javafx.scene.layout.Pane;
import objD.protocol.server.ServerMessage;

public interface ClientState {

    Pane getRootPane();

    void updatePane(ServerMessage fromServer);
}
