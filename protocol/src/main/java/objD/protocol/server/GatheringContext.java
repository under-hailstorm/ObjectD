package objD.protocol.server;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class GatheringContext implements ServerMessage {
    private final List<String> team1;
    private final List<String> team2;
    private final List<String> observers;

    public GatheringContext(List<String> team1, List<String> team2, List<String> observers) {
        this.team1 = team1;
        this.team2 = team2;
        this.observers = observers;

    }

    public List<String> getTeam1() {
        return team1;
    }

    public List<String> getTeam2() {
        return team2;
    }

    public List<String> getObservers() {
        return observers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GatheringContext that = (GatheringContext) o;
        return new EqualsBuilder()
                .append(team1, that.team1)
                .append(team2, that.team2)
                .append(observers, that.observers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).
                append(team1).
                append(team2).
                append(observers).
                toHashCode();
    }
}
