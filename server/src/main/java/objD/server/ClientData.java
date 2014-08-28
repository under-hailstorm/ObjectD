package objD.server;

import objD.protocol.server.ServerMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientData {
    private final String clientName;
    private final ObjectOutputStream os;
    private final Socket socket;
    private Teams team;

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams teams) {
        this.team = teams;
    }

    public String getClientName() {
        return clientName;
    }

    public ClientData(String clientName, ObjectOutputStream os, Socket socket) {
        this.clientName = clientName;
        this.os = os;
        this.socket = socket;
    }


    public void notifyModified(ServerMessage message) throws IOException {
        os.writeObject(message);
        os.flush();
    }
}
