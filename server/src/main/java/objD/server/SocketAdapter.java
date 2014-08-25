package objD.server;


import objD.protocol.client.ClientMessage;
import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketAdapter {

    private final ObjectInputStream is;
    private final ObjectOutputStream os;

    private final Socket clientSocket;

    public SocketAdapter(Socket clientSocket, ObjectInputStream is, ObjectOutputStream os) throws IOException {
        this.clientSocket = clientSocket;
        this.is = is; // new ObjectInputStream(clientSocket.getInputStream());
        this.os = os; // new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public ClientMessage readObject() throws IOException, ClassNotFoundException {
        return (ClientMessage) is.readObject();
    }

    public void writeObject(ServerMessage message) throws IOException {
        os.writeObject(message);
        os.flush();
    }

    public void close() throws IOException {
        is.close();
        os.close();
        clientSocket.close();
    }
}
