package messaging.listeners;

import messaging.listeners.interfaces.IOnMessageSendListener;
import messaging.models.SimpleMessage;
import messaging.tracking.StatefulSession;

import java.io.Serializable;

public class StatefulMessageSentListener implements IOnMessageSendListener {
    private StatefulSession session;

    public StatefulMessageSentListener(StatefulSession session) {
        this.session = session;
    }

    public void onMessageSent(Serializable message) {
        SimpleMessage simpleMessage = (SimpleMessage)message;
        session.sendMessage(simpleMessage.getSender(), simpleMessage.getMessageId());
    }
}
