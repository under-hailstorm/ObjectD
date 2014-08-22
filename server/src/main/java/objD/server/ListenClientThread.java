package objD.server;

import objD.protocol.client.ClientMessage;
import objD.protocol.server.GatheringContext;
import objD.protocol.client.ToObservers;
import objD.protocol.client.ToTeam1;
import objD.protocol.client.ToTeam2;
import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ListenClientThread extends Thread {

    private Map<Class, Long> timeFilter = new HashMap<>();
    private Map<Class, Long> lastPushed = new HashMap<>();
    private boolean needStop = false;
    private final Socket clientSocket;
    private final ObjectOutputStream os;
    private final ObjectInputStream is;
    private final ServerApp serverContext;
    private int clientId;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public ListenClientThread(Socket clientSocket, ObjectOutputStream os, ObjectInputStream is, ServerApp serverContext) {
        this.clientSocket = clientSocket;
        this.serverContext = serverContext;
        this.os = os;
        this.is = is;
    }

    @Override
    public void run() {
        try {

            while (!needStop) {
                Object o = is.readObject();
                Class actionType = o.getClass();
                Long lastPush = lastPushed.get(actionType);
                Long constraint = timeFilter.get(actionType);
                if (lastPush == null || constraint == null || (System.currentTimeMillis() - lastPush > constraint)) {
                    serverContext.addAction((ClientMessage) o, clientName);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Exiting client " + clientName);
            serverContext.removeClient(clientName);
            try {
                os.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyContextModified(ServerMessage context) {
        try {
            os.writeObject(context);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
