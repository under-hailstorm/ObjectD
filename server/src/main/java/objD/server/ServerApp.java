package objD.server;

import objD.protocol.client.ClientMessage;
import objD.protocol.server.NullServerMessage;
import objD.protocol.server.ServerMessage;
import objD.server.states.GatheringState;
import objD.server.states.ServerState;

import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerApp {

    private final int port;
    private ServerState serverState;
    private Queue<ClientMessage> actionsQueue = new ConcurrentLinkedQueue<>();

    private ClientManager clientManager = new ClientManager();


    public ClientManager getClientManager() {
        return clientManager;
    }

    public int getPort() {
        return port;
    }

    public void addAction(ClientMessage clientMessage, String clientName) {
        clientMessage.setClientName(clientName);
        actionsQueue.add(clientMessage);
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
                    server.clientManager.notifyAllSubscribers(serverMessage);
                }

            }
        }, 0, 20, TimeUnit.MILLISECONDS);
    }


    public synchronized void addNewClient(Socket clientSocket, int clientIdCounter) {
        serverState.addNewClient(clientSocket, clientIdCounter);
        clientManager.notifyAllSubscribers(clientManager.toGatheringContext());
    }

    public synchronized void removeClient(String clientName) {
        clientManager.removeClient(clientName);
        clientManager.notifyAllSubscribers(clientManager.toGatheringContext());
    }

}