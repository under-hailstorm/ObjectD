package objD.server;

import objD.protocol.server.GatheringContext;
import objD.protocol.server.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientsManager {

    private static final Logger LOG = LoggerFactory.getLogger(ClientsManager.class);
    private List<ClientData> allClients = new CopyOnWriteArrayList<>();

    public boolean isEmpty() {
        return allClients.isEmpty();
    }

    public boolean containsName(String name) {
        for (ClientData existedClient : allClients) {
            if (existedClient.getClientName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addToObservers(ClientData client) {
        client.setTeam(Teams.OBSERVERS);
        allClients.add(client);
    }

    public void addToLowTeam(ClientData client) {
        int team1Ammount = 0;
        int team2Ammount = 0;
        for (ClientData existedState : allClients) {
            switch (existedState.getTeam()) {
                case TEAM_1:
                    team1Ammount++;
                    break;
                case TEAM_2:
                    team2Ammount++;
                    break;
            }
        }
        if (team1Ammount <= team2Ammount) {
            client.setTeam(Teams.TEAM_1);
        } else {
            client.setTeam(Teams.TEAM_2);
        }

        allClients.add(client);
    }

    public void moveToObservers(String clientName) {
        for (ClientData client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.OBSERVERS);
            }
        }
    }

    public void moveToTeam1(String clientName) {
        for (ClientData client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.TEAM_1);
            }
        }
    }

    public void moveToTeam2(String clientName) {
        for (ClientData client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.TEAM_2);
            }
        }
    }

    public void removeClient(String clientName) {
        for (ClientData client : allClients) {
            if (clientName.equals(client.getClientName())) {
                allClients.remove(client);
            }
        }
    }

    public GatheringContext toGatheringContext() {
        List<String> t1Names = new ArrayList<>();
        List<String> t2Names = new ArrayList<>();
        List<String> observerNames = new ArrayList<>();

        for (ClientData thread : allClients) {
            switch (thread.getTeam()) {
                case TEAM_1:
                    t1Names.add(thread.getClientName());
                    break;
                case TEAM_2:
                    t2Names.add(thread.getClientName());
                    break;
                case OBSERVERS:
                    observerNames.add(thread.getClientName());
                    break;
            }
        }


        return new GatheringContext(t1Names, t2Names, observerNames);
    }

    public void notifyAllSubscribers(ServerMessage message) {
        for (ClientData client : allClients) {
            try {
                client.notifyModified(message);
            } catch (IOException ex) {
                LOG.error("cannot notify client " + client.getClientName(), ex);
            }
        }
    }
}
