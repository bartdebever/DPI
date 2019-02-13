package messaging.listeners.interfaces;

import java.io.Serializable;

public interface IMessageReceivedListener <T extends Serializable> {
    void onMessageReceived(T payload);
}
