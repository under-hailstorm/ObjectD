package objD.model;

public class TankBuilder {
    private String clientName;
    private int teamId;
    private double movementSpeed;
    private double rotationSpeed;

    public TankBuilder withClientName(String name) {
        this.clientName = name;
        return this;
    }

    public TankBuilder withTeamId(int id) {
        this.teamId = id;
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
        Tank result = new Tank(clientName, teamId);
        result.setMovementSpeed(movementSpeed);
        result.setRotationSpeed(rotationSpeed);
        return result;
    }

    public void reset() {
        clientName = null;
        teamId = 0;
        movementSpeed = 0;
        rotationSpeed = 0;
    }
}
