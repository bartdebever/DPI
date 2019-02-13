package messaging.listeners.interfaces;

import messaging.models.SimpleMessage;
import messaging.tracking.StatefulSession;

import java.io.Serializable;

public class StatefulMessageReceivedListener implements IMessageReceivedListener {

    private StatefulSession session;

    public StatefulMessageReceivedListener(StatefulSession session) {
        this.session = session;
    }

    public void onMessageReceived(Serializable message) {
            SimpleMessage simpleMessage = (SimpleMessage)message;
            if (((SimpleMessage) message).getCorrelationId() == null) {
                return;
            }

            String replyTo = session.getSenderByMessage(simpleMessage.getCorrelationId());
            System.out.println(String.format("From: %s To: %s Content: %s", simpleMessage.getSender(), replyTo, simpleMessage.getContent()));
    }
}
