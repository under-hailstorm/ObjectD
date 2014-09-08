package objD.server.states;

import objD.model.GMapFactory;
import objD.protocol.client.*;
import objD.protocol.server.*;
import objD.server.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GatheringState implements ServerState {

    private final ServerApp serverApp;

    private GatheringContext cachedContext;

    public GatheringState(ServerApp serverApp) {
        this.serverApp = serverApp;
    }

    @Override
    public void addNewClient(ClientData clientData) {
        serverApp.getClientsManager().addToLowTeam(clientData);
    }

    @Override
    public ServerMessage processActions() {
        Queue<ClientMessage> actionsQueue = serverApp.getActionsQueue();
        int size = actionsQueue.size();
        ClientsManager clientsManager = serverApp.getClientsManager();
        for (int i = 0; i < size; i++) {
            ClientMessage message = actionsQueue.poll();
            System.out.println("Processing message of type " + message.getClass().getCanonicalName());
            if (message instanceof ToObservers) {
                clientsManager.moveToObservers(message.getClientName());
            }
            if (message instanceof ToTeam1) {
                clientsManager.moveToTeam1(message.getClientName());
            }
            if (message instanceof ToTeam2) {
                clientsManager.moveToTeam2(message.getClientName());
            }
            if (message instanceof Disconnect) {
                clientsManager.removeClient(message.getClientName());
            }
            if (message instanceof StartRequest) {
                InputStream mapIs = getClass().getResourceAsStream("/default.map");
                GMapFactory factory = new GMapFactory();
                StartedState startedState = new StartedState(serverApp, factory.load(mapIs));
                serverApp.setServerState(startedState);
                return new StartOk();
            }
        }
        GatheringContext currentContext = clientsManager.toGatheringContext();
        if (currentContext.equals(cachedContext)) {
            return new NullServerMessage();
        } else {
            cachedContext = currentContext;
            return currentContext;
        }
    }
}
