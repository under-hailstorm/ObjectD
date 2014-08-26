package objD.client;

import javafx.application.Platform;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;

public class SocketListenThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(SocketListenThread.class);
    private final ClientApp clientApp;
    private final SocketAdapter socketAdapter;

    public SocketListenThread(ClientApp clientApp, SocketAdapter socketAdapter) {
        this.clientApp = clientApp;
        this.socketAdapter = socketAdapter;
    }

    @Override
    public void run() {
        boolean connected = true;
        while (connected) {
            try {
                final ServerMessage o = socketAdapter.readObject();
                LOG.debug("recieved mesage of type " + o.getClass().getCanonicalName());
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        clientApp.notifyState(o);
                    }
                });

            } catch (EOFException e) {
                connected = false;
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

    }

}
