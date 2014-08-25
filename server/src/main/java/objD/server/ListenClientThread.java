package objD.server;

import objD.protocol.client.ClientMessage;
import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ListenClientThread extends Thread {

    private Map<Class, Long> timeFilter = new HashMap<>();
    private Map<Class, Long> lastPushed = new HashMap<>();
    private boolean needStop = false;
    private final SocketAdapter socketAdapter;
    private final ServerApp serverContext;
    private String clientName;
    private Teams team;

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams teams) {
        this.team = teams;
    }

    public void setNeedStop(boolean needStop) {
        this.needStop = needStop;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ListenClientThread(SocketAdapter socketAdapter, ServerApp serverContext) {
        this.socketAdapter = socketAdapter;
        this.serverContext = serverContext;
    }

    @Override
    public void run() {
        try {

            while (!needStop) {
                ClientMessage o = socketAdapter.readObject();
                Class actionType = o.getClass();
                Long lastPush = lastPushed.get(actionType);
                Long constraint = timeFilter.get(actionType);
                if (lastPush == null || constraint == null || (System.currentTimeMillis() - lastPush > constraint)) {
                    System.out.println("add action " + o.getClass().getCanonicalName() + " from client " + clientName);
                    serverContext.addAction(o, clientName);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Exiting client " + clientName);
            serverContext.removeClient(clientName);
            try {
                socketAdapter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyContextModified(ServerMessage context) {
        try {
            socketAdapter.writeObject(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
