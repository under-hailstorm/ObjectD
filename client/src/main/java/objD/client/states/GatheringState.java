package objD.client.states;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import objD.client.ClientApp;
import objD.protocol.server.GatheringContext;
import objD.protocol.client.ToObservers;
import objD.protocol.client.ToTeam1;
import objD.protocol.client.ToTeam2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class GatheringState implements ClientState {

    public static final double DEFAULT_WIDTH = 600;
    public static final double DEFAULT_HEIGHT = 600;

    private final ClientApp app;
    private final Socket clientSocket;
    private final ObjectOutputStream os;
    private final GridPane pane = new GridPane();

    private final TableView team1;
    private final TableView team2;
    private final TableView observers;

    private final Button toTeam1 = new Button();
    private final Button toTeam2 = new Button();
    private final Button toObservers = new Button();

    private final Button startButton = new Button();

    public GatheringState(ClientApp app, Socket clientSocket, ObjectOutputStream os) throws IOException {
        this.app = app;
        this.clientSocket = clientSocket;
        this.os = os;

        team1 = aTable("Team1");
        team2 = aTable("Team2");
        observers = aTable("Observers");

        toTeam1.setText("To Team1");
        toTeam1.setOnAction(new ToTeam1EventHandler());

        toTeam2.setText("To Team2");
        toTeam2.setOnAction(new ToTeam2EventHandler());

        toObservers.setText("To Observers");
        toObservers.setOnAction(new ToObserversEventHandler());

        startButton.setText("Start");
        startButton.setOnAction(new StartEventHandler());

        Text startText = new Text("To start click button");

        pane.setHgap(50);
        pane.setVgap(10);
        pane.setPadding(new Insets(0, 50, 0, 50));
        pane.add(toTeam1, 0, 0);
        pane.add(toTeam2, 1, 0);
        pane.add(toObservers, 2, 0);

        pane.add(team1, 0, 1, 2, 1);
        pane.add(team2, 1, 1, 2, 1);
        pane.add(observers, 2, 1, 2, 1);

        pane.add(startText, 3, 1);
        pane.add(startButton, 3, 2);
    }

    private TableView aTable(String name) {
        int width = 90;
        toTeam1.setMinWidth(width);
        toTeam2.setMinWidth(width);
        toObservers.setMinWidth(width);
        TableView aTable = new TableView();
        aTable.setEditable(false);
        aTable.setMaxWidth(width);
        aTable.setMinWidth(width);
        TableColumn col1 = new TableColumn(name);
        col1.setMinWidth(width);
        col1.setCellValueFactory(new PropertyValueFactory<ClientData, String>("name"));
        aTable.getColumns().add(col1);

        return aTable;
    }


    @Override
    public Pane getRootPane() {
        return pane;
    }

    @Override
    public void updatePane(Object fromServer) {

        if (fromServer instanceof GatheringContext) {

            GatheringContext ctx = (GatheringContext) fromServer;

            final ObservableList<ClientData> team1Members = toObservableList(ctx.getTeam1());
            final ObservableList<ClientData> team2Members = toObservableList(ctx.getTeam2());
            final ObservableList<ClientData> obsMembers = toObservableList(ctx.getObservers());


            team1.setItems(team1Members);
            team2.setItems(team2Members);
            observers.setItems(obsMembers);

            toTeam1.setDisable(false);
            for (ClientData clientData : team1Members) {
                if (clientData.getName().equals(app.getClientName())) {
                    toTeam1.setDisable(true);
                }
            }

            toTeam2.setDisable(false);
            for (ClientData clientData : team2Members) {
                if (clientData.getName().equals(app.getClientName())) {
                    toTeam2.setDisable(true);
                }
            }

            toObservers.setDisable(false);
            for (ClientData clientData : obsMembers) {
                if (clientData.getName().equals(app.getClientName())) {
                    toObservers.setDisable(true);
                }
            }
        }
    }

    private ObservableList<ClientData> toObservableList(List<String> members) {
        ObservableList<ClientData> result = FXCollections.observableArrayList();
        for (String member : members) {
            result.add(new ClientData(member));
        }
        return result;
    }

    class ToTeam1EventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                os.writeObject(new ToTeam1());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ToTeam2EventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                os.writeObject(new ToTeam2());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class ToObserversEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                os.writeObject(new ToObservers());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class StartEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                //os.writeObject(new ToObservers());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ClientData {
        private final SimpleStringProperty name;

        public ClientData(String name) {
            this.name = new SimpleStringProperty(name);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

    }
}
