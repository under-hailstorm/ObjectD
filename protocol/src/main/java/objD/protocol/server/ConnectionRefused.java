package objD.protocol.server;

public class ConnectionRefused implements ServerMessage {
    private final String reason;

    public ConnectionRefused(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
