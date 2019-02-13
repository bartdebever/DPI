package messaging.listeners.received;

import messaging.listeners.interfaces.IMessageReceivedListener;
import messaging.models.SimpleMessage;

import java.io.Serializable;

public class SimpleMessageReceivedListener implements IMessageReceivedListener {
    public void onMessageReceived(Serializable payload) {
        SimpleMessage message = (SimpleMessage)payload;
        System.out.println(String.format("%s: %s", message.getSender(), message.getContent()));
    }
}
