package objD.server.states;

import objD.protocol.server.ServerMessage;

import java.net.Socket;

public interface ServerState {

    void addNewClient(Socket clientSocket, int clientIdCounter);

    ServerMessage processActions();
}
