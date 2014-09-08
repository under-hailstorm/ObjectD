package objD.server;

import objD.protocol.client.HelloServer;
import objD.protocol.server.ConnectionRefused;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddClientsThread extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(AddClientsThread.class);

    private final ServerApp serverApp;
    private final ExecutorService clientThreads;


    public AddClientsThread(ServerApp serverContext) {
        this.serverApp = serverContext;
        clientThreads = Executors.newCachedThreadPool();
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(serverApp.getPort())) {
            LOG.info("running server on port " + serverApp.getPort());

            while (true) {
                LOG.info("waiting for new client");

                Socket clientSocket = serverSocket.accept();
                LOG.info("accepting connection request");

                ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());


                try {
                    Object o = is.readObject();
                    if (o instanceof HelloServer) {
                        HelloServer hello = (HelloServer) o;
                        String clientName = hello.getClientName();
                        ClientsManager clientsManager = serverApp.getClientsManager();

                        if (clientsManager.containsName(clientName)) {
                            os.writeObject(new ConnectionRefused("Player with name " + clientName + " is already logged in"));
                            LOG.info("connection refused, client with name " + clientName + " already exists");
                            IOUtils.closeQuietly(clientSocket);
                        } else {
                            serverApp.addNewClient(new ClientData(clientName, os, clientSocket));

                            ListenClient clientThread = new ListenClient(clientName, is, serverApp.getActionsQueue());
                            clientThreads.submit(clientThread);

                            LOG.info("add new client " + clientName);
                        }
                    } else {
                        LOG.info("Connection refused, expecting HelloServer, but found " + o.getClass().getCanonicalName());
                        IOUtils.closeQuietly(clientSocket);
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("exc when expecting HelloServer");
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
