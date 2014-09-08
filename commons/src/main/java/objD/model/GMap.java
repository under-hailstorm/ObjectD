package objD.model;

import java.io.Serializable;
import java.util.*;

public class GMap implements Serializable {
    private List<Tank> allTanks = new ArrayList<>();
    private final MapEntry[][] entries;
    private final Map<Teams, List<RespawnPoint>> respawns = new EnumMap<>(Teams.class);

    public GMap(int rowNum, int columnNum) {
        this.entries = new MapEntry[rowNum][columnNum];
    }

    public int getRowNum() {
        return entries.length;
    }

    public int getColNum() {
        return entries[0].length;
    }

    public MapEntry getEntry(int rowNum, int colNum) {
        int maxCol = entries[0].length;
        if (rowNum % 2 == 0 && colNum == maxCol - 1) {
            throw new ArrayIndexOutOfBoundsException("Even row has one cell less");
        }
        return entries[rowNum][colNum];
    }

    public void addEntry(MapEntry entry) {
        entries[entry.getRowNum()][entry.getColNum()] = entry;
        if (entry instanceof RespawnPoint) {
            RespawnPoint resp = (RespawnPoint) entry;
            if (respawns.get(resp.getTeam()) == null) {
                respawns.put(resp.getTeam(), new ArrayList<RespawnPoint>());
            }
            respawns.get(resp.getTeam()).add(resp);
        }
    }

    public void spawnTank(Tank tank) {
        List<RespawnPoint> respawnPoints = respawns.get(tank.getTeam());
        int index = new Random().nextInt(respawnPoints.size());
        RespawnPoint point = respawnPoints.get(index);
        tank.setCurrentLocation(point);
        allTanks.add(tank);
    }

    public boolean canMoveForward(Tank tank){


        return false;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < entries.length; i++) {
            int colNum = (i % 2 == 0) ? entries[0].length - 1 : entries[0].length;
            for (int j = 0; j < colNum; j++) {
                if (entries[i][j] != null) {
                    result.append(entries[i][j].getClass().getName()).append(" ");
                } else {
                    result.append("null ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}
