package objD.client;


import objD.protocol.client.ClientMessage;
import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketAdapter {
    private int resetCounter;
    private final ObjectInputStream is;
    private final ObjectOutputStream os;

    private final Socket clientSocket;

    public SocketAdapter(Socket clientSocket, ObjectInputStream is, ObjectOutputStream os) throws IOException {
        this.clientSocket = clientSocket;
        this.is = is;
        this.os = os;
    }

    public ServerMessage readObject() throws IOException, ClassNotFoundException {
        return (ServerMessage) is.readObject();
    }

    public void writeObject(ClientMessage message) throws IOException {
        os.writeObject(message);
        os.flush();
        resetCounter++;
        if (resetCounter % 1000 == 0) {
            os.reset();
        }
    }

    public void close() throws IOException {
        is.close();
        os.close();
        clientSocket.close();
    }
}
