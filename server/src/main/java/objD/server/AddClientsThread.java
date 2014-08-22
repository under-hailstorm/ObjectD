package objD.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AddClientsThread extends Thread {

    private int clientIdCounter = 0;
    private final ServerApp serverContext;

    public AddClientsThread(ServerApp serverContext) {
        this.serverContext = serverContext;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(serverContext.getPort())) {
            System.out.println("running server on port " + serverContext.getPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("accepting client # " + clientIdCounter);
                serverContext.addNewClient(clientSocket, clientIdCounter);
                clientIdCounter++;
                System.out.println("waiting for new client");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
