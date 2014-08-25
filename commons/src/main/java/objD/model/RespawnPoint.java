package objD.model;

public class RespawnPoint implements MapEntry {
    private final int teamId;

    public RespawnPoint(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }
}
