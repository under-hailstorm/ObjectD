package objD.client.states;

import javafx.scene.layout.Pane;

public interface ClientState {

    Pane getRootPane();

    void updatePane(Object fromServer);
}
