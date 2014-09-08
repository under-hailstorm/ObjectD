package objD.server;

import objD.protocol.client.ClientMessage;
import objD.protocol.client.Disconnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class ListenClient implements Runnable {

    private Map<Class, Long> timeFilter = new HashMap<>();
    private Map<Class, Long> lastPushed = new HashMap<>();

    private final String clientName;
    private final ObjectInputStream is;
    private final Queue<ClientMessage> outputQueue;


    public ListenClient(String clientName, ObjectInputStream is, Queue<ClientMessage> outputQueue) {
        this.clientName = clientName;
        this.is = is;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ClientMessage clientMessage = (ClientMessage) is.readObject();
                Class actionType = clientMessage.getClass();
                Long lastPush = lastPushed.get(actionType);
                Long constraint = timeFilter.get(actionType);
                if (lastPush == null || constraint == null || (System.currentTimeMillis() - lastPush > constraint)) {
                    System.out.println("add action " + clientMessage.getClass().getCanonicalName() + " from client " + clientName);
                    clientMessage.setClientName(clientName);
                    outputQueue.add(clientMessage);
                }
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Exiting client " + clientName);
            Disconnect messDisconnect = new Disconnect();
            messDisconnect.setClientName(clientName);
            outputQueue.add(messDisconnect);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
