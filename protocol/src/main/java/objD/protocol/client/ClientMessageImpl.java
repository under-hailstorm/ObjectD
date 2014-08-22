package objD.protocol.client;

public class ClientMessageImpl implements ClientMessage {

    protected String clientName;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getClientName() {
        return clientName;
    }
}
