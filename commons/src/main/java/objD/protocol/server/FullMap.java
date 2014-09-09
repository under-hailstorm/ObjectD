package objD.protocol.server;

import objD.model.GMap;

public class FullMap implements ServerMessage {
    private final GMap map;

    public FullMap(GMap map) {
        this.map = map;
    }

    public GMap getMap() {
        return map;
    }
}
