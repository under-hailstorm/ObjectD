package objD.server.states;

import objD.protocol.server.ServerMessage;
import objD.server.SocketAdapter;

public interface ServerState {

    void addNewClient(SocketAdapter socketAdapter);

    ServerMessage processActions();
}
