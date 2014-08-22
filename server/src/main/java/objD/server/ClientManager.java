package objD.server;

import objD.protocol.server.ConnectionRefused;
import objD.protocol.server.GatheringContext;
import objD.protocol.server.ServerMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientManager {

    private List<ListenClientThread> allClients = new CopyOnWriteArrayList<>();

    public boolean containsName(String name) {
        for (ListenClientThread existedState : allClients) {
            if (existedState.getClientName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addToLowTeam(ListenClientThread thread) {
        int team1Ammount = 0;
        int team2Ammount = 0;
        for (ListenClientThread existedState : allClients) {
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
            thread.setTeam(Teams.TEAM_1);
        } else {
            thread.setTeam(Teams.TEAM_2);
        }

        allClients.add(thread);
    }

    public void moveToObservers(String clientName) {
        for (ListenClientThread client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.OBSERVERS);
            }
        }
    }

    public void moveToTeam1(String clientName) {
        for (ListenClientThread client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.TEAM_1);
            }
        }
    }

    public void moveToTeam2(String clientName) {
        for (ListenClientThread client : allClients) {
            if (clientName.equals(client.getClientName())) {
                client.setTeam(Teams.TEAM_2);
            }
        }
    }

    public void removeClient(String clientName) {
        for (ListenClientThread client : allClients) {
            if (clientName.equals(client.getClientName())) {
                allClients.remove(client);
            }
        }
    }

    public GatheringContext toGatheringContext() {
        List<String> t1Names = new ArrayList<>();
        List<String> t2Names = new ArrayList<>();
        List<String> observerNames = new ArrayList<>();

        for (ListenClientThread thread : allClients) {
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

    public void notifyAllSubscribers(ServerMessage context) {
        for (ListenClientThread state : allClients) {
            state.notifyContextModified(context);
        }
    }
}
