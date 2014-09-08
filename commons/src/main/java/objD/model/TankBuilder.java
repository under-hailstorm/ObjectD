package objD.model;

public class TankBuilder {
    private static final double DEFAULT_MOVEMENT_SPEED = 10;
    private static final double DEFAULT_ROTATION_SPEED = 10;
    private String clientName;
    private Teams team;
    private double movementSpeed = DEFAULT_MOVEMENT_SPEED;
    private double rotationSpeed = DEFAULT_ROTATION_SPEED;

    public TankBuilder withClientName(String name) {
        this.clientName = name;
        return this;
    }

    public TankBuilder withTeam(Teams team) {
        this.team = team;
        return this;
    }


    public TankBuilder withMovementSpeed(double speed) {
        this.movementSpeed = speed;
        return this;
    }

    public TankBuilder withRotationSpeed(double speed) {
        this.rotationSpeed = speed;
        return this;
    }

    public Tank build() {
        Tank result = new Tank(clientName, team);
        result.setMovementSpeed(movementSpeed);
        result.setRotationSpeed(rotationSpeed);
        return result;
    }

    public void reset() {
        clientName = null;
        team = null;
        movementSpeed = DEFAULT_MOVEMENT_SPEED;
        rotationSpeed = DEFAULT_ROTATION_SPEED;
    }
}
