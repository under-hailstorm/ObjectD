package objD.server.states;

import objD.protocol.client.*;
import objD.protocol.server.*;
import objD.server.ClientManager;
import objD.server.SocketAdapter;
import objD.server.ListenClientThread;
import objD.server.ServerApp;

import java.io.IOException;
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
    public void addNewClient(SocketAdapter socketAdapter) {
        Object o;
        try {
            o = socketAdapter.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("exc when creating i/o streams");
            ex.printStackTrace();
            return;
        }


        if (o instanceof HelloServer) {
            HelloServer hello = (HelloServer) o;
            String clientName = hello.getClientName();
            ClientManager clientManager = serverApp.getClientManager();
            try {
                if (clientManager.containsName(clientName)) {
                    socketAdapter.writeObject(new ConnectionRefused("Player with name " + clientName + " is already logged in"));
                    socketAdapter.close();
                    return;
                }

            } catch (IOException ex) {
                return;
            }

            ListenClientThread clientThread = new ListenClientThread(socketAdapter, serverApp);

            clientThread.setClientName(clientName);
            clientManager.addToLowTeam(clientThread);
            clientThread.start();
            System.out.println("Start " + clientName + " thread");

        } else {
            System.out.println("Expecting HelloServer, but found " + o.getClass().getCanonicalName());
            System.out.println("closing connection");
            try {
                socketAdapter.close();
            } catch (IOException ex) {
            }
        }

    }

    @Override
    public ServerMessage processActions() {
        Queue<ClientMessage> actionsQueue = serverApp.getActionsQueue();
        int size = actionsQueue.size();
        List<ClientMessage> toProcess = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            toProcess.add(actionsQueue.poll());
        }
        ClientManager clientManager = serverApp.getClientManager();
        for (ClientMessage message : toProcess) {
            System.out.println("Processing message of type " + message.getClass().getCanonicalName());
            if (message instanceof ToObservers) {
                clientManager.moveToObservers(message.getClientName());
            }
            if (message instanceof ToTeam1) {
                clientManager.moveToTeam1(message.getClientName());
            }
            if (message instanceof ToTeam2) {
                clientManager.moveToTeam2(message.getClientName());
            }
            if (message instanceof StartRequest) {
                StartedState startedState = new StartedState(serverApp);
                serverApp.setServerState(startedState);
                return new StartOk();
            }
        }
        GatheringContext currentContext = clientManager.toGatheringContext();
        if (currentContext.equals(cachedContext)) {
            return new NullServerMessage();
        } else {
            cachedContext = currentContext;
            return currentContext;
        }
    }
}
