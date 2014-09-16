package objD.server;

import objD.protocol.client.ClientMessage;
import objD.protocol.server.NullServerMessage;
import objD.protocol.server.ServerMessage;
import objD.server.states.GatheringState;
import objD.server.states.ServerState;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerApp {

    private final int port;
    private ServerState serverState;
    private Queue<ClientMessage> actionsQueue = new ConcurrentLinkedQueue<>();

    private ClientsManager clientsManager = new ClientsManager();


    public ClientsManager getClientsManager() {
        return clientsManager;
    }

    public int getPort() {
        return port;
    }

    public Queue<ClientMessage> getActionsQueue() {
        return actionsQueue;
    }

    public void setServerState(ServerState serverState) {
        this.serverState = serverState;
    }

    public ServerApp(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        String portStr = System.getProperty("server.port", "9999");
        final ServerApp server = new ServerApp(Integer.parseInt(portStr));
        GatheringState gatheringState = new GatheringState(server);
        server.setServerState(gatheringState);

        AddClientsThread th = new AddClientsThread(server);
        th.start();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ServerMessage serverMessage = server.serverState.processActions();
                if (!(serverMessage instanceof NullServerMessage)) {
                    server.clientsManager.notifyAllSubscribers(serverMessage);
                }

            }
        }, 0, 20, TimeUnit.MILLISECONDS);
    }


    public synchronized void addNewClient(ClientData clientData) {
        serverState.addNewClient(clientData);
        clientsManager.notifyAllSubscribers(clientsManager.toGatheringContext());
    }

    public synchronized void removeClient(String clientName) {
        clientsManager.removeClient(clientName);
        clientsManager.notifyAllSubscribers(clientsManager.toGatheringContext());
    }

}
