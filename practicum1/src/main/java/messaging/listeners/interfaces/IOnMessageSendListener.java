package messaging.listeners.interfaces;

import java.io.Serializable;

public interface IOnMessageSendListener {
    void onMessageSent(Serializable message);
}
