package objD.server.states;

import objD.model.GMap;
import objD.protocol.client.HelloServer;
import objD.protocol.server.ConnectionRefused;
import objD.protocol.server.MapUpdate;
import objD.protocol.server.ServerMessage;
import objD.server.*;

import java.io.IOException;

public class StartedState implements ServerState {

    private final ServerApp serverApp;
    private final ClientsManager clientsManager;
    private final GMap gMap;

    public StartedState(ServerApp serverApp, GMap gMap) {
        this.serverApp = serverApp;
        this.gMap = gMap;

        this.clientsManager = serverApp.getClientsManager();
        //spawn tanks for teams
//        for(ListenClientThread thread: clientsManager.){
//
//        }
    }

    @Override
    public void addNewClient(ClientData clientData) {
        clientsManager.addToObservers(clientData);
    }

    @Override
    public ServerMessage processActions() {
        if (clientsManager.isEmpty()) {
            GatheringState gatheringState = new GatheringState(serverApp);
            serverApp.setServerState(gatheringState);
        }
        return new MapUpdate();
    }
}
