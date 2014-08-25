package objD.server.states;

import objD.protocol.client.HelloServer;
import objD.protocol.server.ConnectionRefused;
import objD.protocol.server.MapUpdate;
import objD.protocol.server.ServerMessage;
import objD.server.ClientManager;
import objD.server.SocketAdapter;
import objD.server.ListenClientThread;
import objD.server.ServerApp;

import java.io.IOException;

public class StartedState implements ServerState {

    private final ServerApp serverApp;

    public StartedState(ServerApp serverApp) {
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
            clientManager.addToObservers(clientThread);
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
        return new MapUpdate();
    }
}
