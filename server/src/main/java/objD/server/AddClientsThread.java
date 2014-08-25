package objD.server;


import objD.protocol.client.ClientMessage;
import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AddClientsThread extends Thread {

    private final ServerApp serverContext;

    public AddClientsThread(ServerApp serverContext) {
        this.serverContext = serverContext;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(serverContext.getPort())) {
            System.out.println("running server on port " + serverContext.getPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("accepting client # ");
                ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
                serverContext.addNewClient(new SocketAdapter(clientSocket, is, os));
                System.out.println("waiting for new client");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
