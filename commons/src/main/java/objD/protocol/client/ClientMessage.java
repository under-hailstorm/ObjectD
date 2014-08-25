package objD.protocol.client;

import java.io.Serializable;

public interface ClientMessage extends Serializable {

    String getClientName();
    void setClientName(String clientName);
}
