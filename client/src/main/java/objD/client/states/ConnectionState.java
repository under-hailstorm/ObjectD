package objD.client.states;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import objD.client.ClientApp;
import objD.client.SocketAdapter;
import objD.client.SocketListenThread;
import objD.protocol.server.ConnectionRefused;
import objD.protocol.server.GatheringContext;
import objD.protocol.client.HelloServer;
import objD.protocol.server.ServerMessage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionState implements ClientState {
    private static final int DEFAULT_SERVER_PORT = 9999;
    private Label errorLabel = new Label();
    private final TextField serverHostField = new TextField("localhost");
    private final TextField nameField = new TextField("client1");
    private final VBox pane = new VBox();

    private final ClientApp clientApp;
    private SocketAdapter socketAdapter;

    public ConnectionState(ClientApp app) {
        clientApp = app;
        serverHostField.setMaxWidth(100);
        nameField.setMaxWidth(100);
        Button btn = new Button();
        btn.getStyleClass().add("loginButton");
        btn.setText("Connect");
        btn.setOnAction(new ConnectButtonEventHandler());

        HBox hostAndPort = new HBox();
        hostAndPort.setSpacing(10);
        hostAndPort.getChildren().add(aLabel("Host:"));
        hostAndPort.getChildren().add(serverHostField);
        hostAndPort.setAlignment(Pos.CENTER);


        HBox nameHbox = new HBox();
        nameHbox.setSpacing(10);
        nameHbox.getChildren().add(aLabel("Your name:"));
        nameHbox.getChildren().add(nameField);
        nameHbox.setAlignment(Pos.CENTER);

        pane.setAlignment(Pos.CENTER);
        pane.setFillWidth(true);
        pane.setSpacing(30);
        pane.getChildren().add(aLabel("Connection to the server"));
        pane.getChildren().add(hostAndPort);
        pane.getChildren().add(nameHbox);
        pane.getChildren().add(errorLabel);
        pane.getChildren().add(btn);
    }

    @Override
    public Pane getRootPane() {
        return pane;
    }

    @Override
    public void updatePane(ServerMessage fromServer) {
        if (fromServer instanceof ConnectionRefused) {
            final ConnectionRefused cr = (ConnectionRefused) fromServer;
            errorLabel.setText(cr.getReason() + " Default port is " + DEFAULT_SERVER_PORT);
        }
        if (fromServer instanceof GatheringContext) {
            try {
                GatheringState gathering = new GatheringState(clientApp, socketAdapter);
                gathering.updatePane(fromServer);
                clientApp.setTitle(ClientApp.APP_NAME + " " + nameField.getText());
                clientApp.setClientName(nameField.getText());
                clientApp.setWindowSize(GatheringState.DEFAULT_WIDTH, GatheringState.DEFAULT_HEIGHT);
                clientApp.setCurrentState(gathering);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Label aLabel(String text) {
        Label result = new Label(text);
        result.getStyleClass().add("loginLabel");
        return result;
    }

    class ConnectButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String hostText = serverHostField.getText();
            String host;
            int port;
            if (hostText.contains(":")) {
                host = hostText.substring(0, hostText.indexOf(':'));
                try {
                    port = Integer.parseInt(hostText.substring(hostText.indexOf(':') + 1));
                } catch (NumberFormatException e) {
                    errorLabel.setText("Invalid port " + hostText.substring(hostText.indexOf(':') + 1));
                    return;
                }
            } else {
                host = hostText;
                port = DEFAULT_SERVER_PORT;
            }
            try {

                Socket clientSocket = new Socket(host, port);

                ObjectOutputStream os = new ObjectOutputStream(clientSocket.getOutputStream());
                os.writeObject(new HelloServer(nameField.getText()));
                ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());
                socketAdapter = new SocketAdapter(clientSocket, is, os);

                SocketListenThread thread = new SocketListenThread(clientApp, socketAdapter);
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {
                errorLabel.setText(e.getLocalizedMessage());
            }
        }
    }
}
