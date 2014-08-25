package objD.model;

import java.io.Serializable;

public class Tank implements Serializable {
    private final String clientName;
    private final int teamId;
    private double movementSpeed;
    private double rotationSpeed;

    private long movementStartTime;
    private long rotationStartTime;
    private MovementDirection movementDirection;
    private RotationDirection rotationDirection;

    public Tank(String clientName, int teamId) {
        this.clientName = clientName;
        this.teamId = teamId;
    }

    public long getMovementStartTime() {
        return movementStartTime;
    }

    public void setMovementStartTime(long movementStartTime) {
        this.movementStartTime = movementStartTime;
    }

    public long getRotationStartTime() {
        return rotationStartTime;
    }

    public void setRotationStartTime(long rotationStartTime) {
        this.rotationStartTime = rotationStartTime;
    }

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    public RotationDirection getRotationDirection() {
        return rotationDirection;
    }

    public void setRotationDirection(RotationDirection rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public String getClientName() {
        return clientName;
    }

    public int getTeamId() {
        return teamId;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public static enum MovementDirection {
        FORWARD, BACKWARD
    }

    public static enum RotationDirection {
        CLOCKWISE, COUNTERCLOCKWISE
    }
}
