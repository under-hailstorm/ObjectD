package objD.server.states;

import objD.protocol.server.ServerMessage;
import objD.server.ClientData;
import objD.server.SocketAdapter;

public interface ServerState {

    void addNewClient(ClientData clientData);

    ServerMessage processActions();
}
