package objD.server.states;

import objD.model.GMap;
import objD.model.Tank;
import objD.protocol.client.HelloServer;
import objD.protocol.server.ConnectionRefused;
import objD.protocol.server.FullMap;
import objD.protocol.server.MapUpdate;
import objD.protocol.server.ServerMessage;
import objD.server.*;

import java.io.IOException;
import java.util.List;

public class StartedState implements ServerState {
    private static final int AMMOUNT_FOR_RESET = 1000;
    private int messagesAmmount;
    private final ServerApp serverApp;
    private final ClientsManager clientsManager;
    private final GMap gMap;

    public StartedState(ServerApp serverApp, GMap gMap) {
        this.serverApp = serverApp;
        this.gMap = gMap;

        this.clientsManager = serverApp.getClientsManager();
        //spawn tanks for teams
        List<Tank> tanks = clientsManager.buildAllTanks();
        for (Tank tank : tanks) {
            gMap.spawnTank(tank);
        }
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
        if (messagesAmmount % AMMOUNT_FOR_RESET == 0) {
            return new FullMap(gMap);
        }
        return new MapUpdate();
    }
}
