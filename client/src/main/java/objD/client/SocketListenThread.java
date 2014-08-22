package objD.client;

import javafx.application.Platform;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SocketListenThread extends Thread {
    private final ClientApp app;
    private final ObjectInputStream is;

    public SocketListenThread(ClientApp app, ObjectInputStream is) {
        this.app = app;
        this.is = is;
    }

    @Override
    public void run() {
        boolean connected = true;
        while (connected) {
            try {
                final Object o = is.readObject();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        app.notifyState(o);
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
