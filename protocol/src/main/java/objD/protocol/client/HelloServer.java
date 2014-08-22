package objD.protocol.client;


public class HelloServer extends ClientMessageImpl {

    public HelloServer(String clientName) {
        this.clientName = clientName;
    }
}
