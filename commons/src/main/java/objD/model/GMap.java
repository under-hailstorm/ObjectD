package objD.model;

import java.io.Serializable;
import java.util.*;

public class GMap implements Serializable {
    private List<Tank> allTanks = new ArrayList<>();
    private final MapEntry[][] entries;
    private final Map<Teams, List<RespawnPoint>> respawns = new EnumMap<>(Teams.class);

    public GMap(int rowNum, int columnNum) {
        this.entries = new MapEntry[rowNum][columnNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                entries[i][j] = new EmptyEntry(i, j);
            }
        }
    }

    public int getRowNum() {
        return entries.length;
    }

    public int getColNum() {
        return entries[0].length;
    }

    public MapEntry getEntry(int rowNum, int colNum) {
        int maxCol = entries[0].length;
        if (rowNum % 2 == 1 && colNum == maxCol - 1) {
            throw new ArrayIndexOutOfBoundsException("Odd row has one cell less");
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
        tank.setHeadDirection(MapDirection.NORTH);
        allTanks.add(tank);
    }

    public MapEntry getClosestEntry(MapEntry currentLocation, MapDirection direction) {
        int colNum = currentLocation.getColNum();
        int rowNum = currentLocation.getRowNum();
        if (rowNum < 0 || rowNum >= entries.length) {
            throw new ArrayIndexOutOfBoundsException("incorrect row num " + rowNum);
        }
        if (colNum < 0 || colNum >= entries[0].length) {
            throw new ArrayIndexOutOfBoundsException("incorrect column num " + colNum);
        }
        if (rowNum % 2 == 1 && colNum == entries[0].length - 1) {
            throw new ArrayIndexOutOfBoundsException("Odd row has one cell less");
        }

        switch (direction) {
            case NORTH:
                if (rowNum > 1) {
                    return entries[rowNum - 2][colNum];
                }
                return null;
            case NORTH_EAST:
                if (rowNum % 2 == 0) {
                    if (rowNum > 0 && colNum < entries[0].length - 1) {
                        return entries[rowNum - 1][colNum];
                    } else {
                        return null;
                    }
                } else {
                    return entries[rowNum - 1][colNum + 1];
                }
            case NORTH_WEST:
                if (rowNum % 2 == 0) {
                    if (rowNum > 0 && colNum > 0) {
                        return entries[rowNum - 1][colNum - 1];
                    } else {
                        return null;
                    }
                } else {
                    return entries[rowNum - 1][colNum];
                }
            case SOUTH:
                if (rowNum < entries.length - 2) {
                    return entries[rowNum + 2][colNum];
                }
                return null;
            case SOUTH_EAST:
                if (rowNum == entries.length - 1) {
                    return null;
                }
                if (rowNum % 2 == 0) {
                    if (colNum < entries[0].length - 1) {
                        return entries[rowNum + 1][colNum];
                    } else {
                        return null;
                    }
                } else {
                    return entries[rowNum + 1][colNum + 1];
                }
            case SOUTH_WEST:
                if (rowNum == entries.length - 1) {
                    return null;
                }
                if (rowNum % 2 == 0) {
                    if (colNum > 0) {
                        return entries[rowNum + 1][colNum - 1];
                    } else {
                        return null;
                    }
                } else {
                    return entries[rowNum + 1][colNum];
                }
        }
        return null;
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
