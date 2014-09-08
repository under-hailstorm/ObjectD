package objD.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RespawnPoint extends BaseMapEntry {
    private final Teams team;

    public RespawnPoint(int x, int y, Teams teamId) {
        super(x, y);
        this.team = teamId;
    }

    public Teams getTeam() {
        return team;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(rowNum).
                append(colNum).
                append(team).
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
                .append(team, that.team)
                .isEquals();
    }
}
