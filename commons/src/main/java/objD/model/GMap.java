package objD.model;

import java.io.Serializable;
import java.util.*;

public class GMap implements Serializable {
    private List<Tank> allTanks = new ArrayList<>();
    private final MapEntry[][] entries;
    private final Map<Integer, List<RespawnPoint>> respawns = new HashMap<>();

    public GMap(MapEntry[][] entries) {
        this.entries = entries;

        for (MapEntry[] aRow : entries) {
            for (MapEntry entry : aRow) {
                if (entry != null && entry instanceof RespawnPoint) {
                    RespawnPoint resp = (RespawnPoint) entry;
                    if (!respawns.containsKey(resp.getTeamId())) {
                        respawns.put(resp.getTeamId(), new ArrayList<RespawnPoint>());
                    }
                    List<RespawnPoint> resps = respawns.get(resp.getTeamId());
                    resps.add(resp);
                }
            }
        }
    }

    public void spawnTank(Tank tank) {
        List<RespawnPoint> respawnPoints = respawns.get(tank.getTeamId());
        int index = new Random().nextInt(respawnPoints.size());
        RespawnPoint point = respawnPoints.get(index);

    }
}
