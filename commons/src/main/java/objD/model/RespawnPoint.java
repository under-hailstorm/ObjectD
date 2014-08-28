package objD.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RespawnPoint extends BaseMapEntry {
    private final int teamId;

    public RespawnPoint(int x, int y, int teamId) {
        super(x, y);
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(rowNum).
                append(colNum).
                append(teamId).
                toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        RespawnPoint that = (RespawnPoint) obj;
        return new EqualsBuilder()
                .append(rowNum, that.rowNum)
                .append(colNum, that.colNum)
                .append(teamId, that.teamId)
                .isEquals();
    }
}
