package messaging.listeners;

import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefullSession;

import java.io.Serializable;

public class StatefulMessageSentListener implements IOnMessageSendListener {
    private StatefullSession session;

    public StatefulMessageSentListener(StatefullSession session) {
        this.session = session;
    }

    public void onMessageSent(Serializable message) {
        SimpleMessage simpleMessage = (SimpleMessage)message;
        session.sendMessage(simpleMessage.getSender(), simpleMessage.getMessageId());
    }
}
